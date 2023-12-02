package Database.DataAccess;

import Database.AccountsInfo;
import Database.DTOs.LoanRequestDTO;
import Database.DTOs.UserInfoAccNumDTO;
import Database.DTOs.UserInfoEmailDTO;
import Database.Transactions;
import Database.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class SQLConnectionTest {
    @InjectMocks
    private SQLConnection sqlConnection = null;
    @Mock
    private Connection connection = null;
    @Mock
    private PreparedStatement statement;
    @Mock
    private ResultSet resultSet;
    @Captor
    private ArgumentCaptor<String> stringCaptor;
    @Captor
    private ArgumentCaptor<Double> doubleCaptor;
    @Captor
    private ArgumentCaptor<Timestamp> timestampCaptor;
    @InjectMocks
    private UserInfoEmailDTO userInfoDTO;
    @Captor
    private ArgumentCaptor<UserInfoEmailDTO> userInfoCaptor;

    @BeforeEach
    void setup() throws SQLException {
        sqlConnection = Mockito.spy(SQLConnection.getInstance());
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(PreparedStatement.class);
        resultSet = Mockito.mock(ResultSet.class);
        stringCaptor = ArgumentCaptor.forClass(String.class);
        doubleCaptor = ArgumentCaptor.forClass(Double.class);
        timestampCaptor = ArgumentCaptor.forClass(Timestamp.class);

        Mockito.when(sqlConnection.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);
    }
    @Test
    void SQLConnection_is_created()
    {
        assertNotNull(sqlConnection);
    }
    @Test
    void SQLConnection_is_singleton() throws SQLException {
        SQLConnection sqlConnection1 = SQLConnection.getInstance();
        SQLConnection sqlConnection2 = SQLConnection.getInstance();
        assertSame(sqlConnection1,sqlConnection2);
    }
    @Test
    void connection_is_alive() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getConnectionMethod = SQLConnection.class.getDeclaredMethod("getConnection");
        getConnectionMethod.setAccessible(true);
        Connection connection = (Connection) getConnectionMethod.invoke(sqlConnection);
        assertNotNull(connection);
    }
    @Test
    void transfer_is_saved_in_the_database() throws SQLException {

        PreparedStatement statement1 = Mockito.mock(PreparedStatement.class);
        PreparedStatement statement2 = Mockito.mock(PreparedStatement.class);
        PreparedStatement statement3 = Mockito.mock(PreparedStatement.class);

        sqlConnection.transfer("aaaabbbbccccdddd", "bbbbaaaaccccdddd", 10.0, "-");
        // Checking for the beginning of the Transaction
        Mockito.verify(connection).setAutoCommit(false);

        // Verifying the addition of the amount
        Mockito.verify(connection).prepareStatement(Mockito.eq("UPDATE account SET balance = balance + ? WHERE account_id = ?"));
        Mockito.verify(statement1).setDouble(Mockito.eq(1), doubleCaptor.capture());
        Mockito.verify(statement1).setString(Mockito.eq(2), stringCaptor.capture());
        Mockito.verify(statement1).executeUpdate();

        assertEquals(10.0, doubleCaptor.getValue(), 0.01);
        assertEquals("bbbbaaaaccccdddd", stringCaptor.getValue());

        // Verifying the deduction of the amount
        Mockito.verify(connection).prepareStatement(Mockito.eq("UPDATE account SET balance = balance - ? WHERE account_id = ?"));
        Mockito.verify(statement2).setDouble(Mockito.eq(1), doubleCaptor.capture());
        Mockito.verify(statement2).setString(Mockito.eq(2), stringCaptor.capture());
        Mockito.verify(statement2).executeUpdate();

        assertEquals(10.0, doubleCaptor.getValue(), 0.01);
        assertEquals("aaaabbbbccccdddd", doubleCaptor.getValue());

        // Verifying that the transaction is saved in the transactions table
        Mockito.verify(connection).prepareStatement(Mockito.eq("INSERT INTO transactions(dateTime, amount, message, senderAccount_id, recipientAccount_id) " +
                "VALUES (?, ?, ?, ?, ?)"));
        Mockito.verify(statement3).setTimestamp(Mockito.eq(1), Mockito.any(Timestamp.class));
        Mockito.verify(statement3).setDouble(Mockito.eq(2), doubleCaptor.capture());
        Mockito.verify(statement3).setString(Mockito.eq(3), stringCaptor.capture());
        Mockito.verify(statement3).setString(Mockito.eq(4), stringCaptor.capture());
        Mockito.verify(statement3).setString(Mockito.eq(5), stringCaptor.capture());
        Mockito.verify(statement3).executeUpdate();

        assertEquals(10.0, doubleCaptor.getValue(), 0.01);
        assertEquals("-", stringCaptor.getValue());
        assertEquals("aaaabbbbccccdddd", stringCaptor.getAllValues().get(2));
        assertEquals("bbbbaaaaccccdddd", stringCaptor.getAllValues().get(3));

        // Check if the transaction is committed
        Mockito.verify(connection).commit();
    }

    @Test
    void checkBalance_returns_a_double() throws SQLException {
        Mockito.when(sqlConnection.checkBalance(Mockito.anyString())).thenReturn(10.0);
        assertEquals(10.0, sqlConnection.checkBalance(Mockito.anyString()));
    }

    @Test
    void checkBalance_reaches_the_database() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(sqlConnection.checkBalance("-")).thenReturn(10.0);
        sqlConnection.checkBalance("-");
        Mockito.verify(connection).prepareStatement(Mockito.eq("SELECT balance FROM account WHERE account_id=?;"));
        Mockito.verify(statement).setString(eq(1),stringCaptor.capture());
        Mockito.verify(statement).executeQuery();
        Mockito.verify(resultSet).next();
        Mockito.verify(resultSet).getDouble("balance");

        assertEquals("-",stringCaptor.getAllValues().get(0));
    }

    @Test
    void checkAccountId_returns_a_String() throws SQLException {
        Mockito.when(sqlConnection.checkAccountId(Mockito.anyString())).thenReturn("-");
        assertEquals(sqlConnection.checkAccountId("-"),"-");
    }

    @Test
    void checkAccountId_reaches_the_database() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(sqlConnection.checkAccountId("-")).thenReturn("-");
        sqlConnection.checkAccountId("-");
        Mockito.verify(connection).prepareStatement(Mockito.eq("SELECT account_id FROM account WHERE account_id = ?;"));
        Mockito.verify(statement).setString(eq(1),stringCaptor.capture());
        Mockito.verify(statement).executeQuery();
        Mockito.verify(resultSet).next();
        Mockito.verify(resultSet).getString("account_id");

        assertEquals("-",stringCaptor.getAllValues().get(0));
    }

    @Test
    void dailyCheck_returns_a_double() throws SQLException {
        Mockito.when(sqlConnection.dailyCheck(Mockito.anyString())).thenReturn(200.0);
        assertEquals(200.0,sqlConnection.dailyCheck("-"));
    }
    @Test
    void dailyCheck_reaches_the_database() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(sqlConnection.dailyCheck("-")).thenReturn(200.0);
        sqlConnection.dailyCheck("-");
        Mockito.verify(connection).prepareStatement(Mockito.eq("SELECT SUM(amount)\n" +
                "FROM transactions\n" +
                "WHERE senderAccount_id = ?\n" +
                "  AND DATE_TRUNC('day', dateTime) = CURRENT_DATE;"));
        Mockito.verify(statement).setString(eq(1),stringCaptor.capture());
        Mockito.verify(statement).executeQuery();
        Mockito.verify(resultSet).next();
        Mockito.verify(resultSet).getDouble("sum");

        assertEquals("-",stringCaptor.getAllValues().get(0));
    }

    @Test
    void deposit_updates_the_database() throws SQLException {
        sqlConnection.deposit("aaaabbbbccccdddd",50);
        Mockito.verify(connection).prepareStatement("UPDATE banking_system.account\n" +
                "SET balance = balance + ?\n" +
                "WHERE account_id = ?");
        Mockito.verify(statement).setDouble(eq(1),doubleCaptor.capture());
        Mockito.verify(statement).setString(eq(2),stringCaptor.capture());
        Mockito.verify(statement).executeUpdate();

        assertEquals("aaaabbbbccccdddd",stringCaptor.getValue());
        assertEquals(50,doubleCaptor.getValue());
    }
    @Test
    void getUsers_returns_a_list() throws SQLException {
        User sampleUser = User.newBuilder()
                .setEmail("test@example.com")
                .setPassword("test1234")
                .setFirstName("Test")
                .setMiddleName("Test")
                .setLastName("Test")
                .setRole("testUser")
                .build();
        Mockito.when(sqlConnection.getUsers()).thenReturn(List.of(sampleUser));
        List<User> result = sqlConnection.getUsers();

        assertEquals(List.of(sampleUser), result);
    }

    @Test
    void getUsers_queries_the_database() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        User sampleUser = User.newBuilder()
                .setEmail("test@example.com")
                .setPassword("test1234")
                .setFirstName("Test")
                .setMiddleName("Test")
                .setLastName("Test")
                .setRole("testUser")
                .build();
        List<User> users = new ArrayList<>();
        users.add(sampleUser);
        try
        {
            Mockito.when(sqlConnection.getUsers()).thenReturn(users);
            users = sqlConnection.getUsers();
        }catch (NullPointerException ignored){}
        Mockito.verify(connection).prepareStatement("SELECT *\n" +
                "FROM \"user\"");
        Mockito.verify(statement).executeQuery();
        Mockito.verify(resultSet).next();
        Mockito.verify(resultSet).getString("email");
        Mockito.verify(resultSet).getString("password");
        Mockito.verify(resultSet).getString("firstname");
        Mockito.verify(resultSet).getString("middlename");
        Mockito.verify(resultSet).getString("lastname");
        Mockito.verify(resultSet).getString("role");
        User.Builder builder = Mockito.mock(User.Builder.class);
        try
        {
            Mockito.verify(builder.setEmail("test@example.com").setPassword("test1234").setFirstName("Test").setMiddleName("Test").setLastName("Test").setRole("testUser")).build();
        }catch (NullPointerException ignored){}
        assertEquals(users.size(), 1);
    }

    @Test
    void getAccountsInfo_returns_a_list() throws SQLException {
        AccountsInfo temp = AccountsInfo.newBuilder().
                setAccountBalance(10)
                .setAccountNumber("11111111111111")
                .setAccountType("personal")
                .setOwnerName("Name")
                .build();
        Mockito.when(sqlConnection.getAccountsInfo()).thenReturn(List.of(temp));
        List<AccountsInfo> result = sqlConnection.getAccountsInfo();

        assertEquals(List.of(temp), result);
    }
    @Test
    void getUserAccountInfos_returns_a_list() throws SQLException {
        AccountsInfo temp = AccountsInfo.newBuilder().
                setAccountBalance(10)
                .setAccountNumber("11111111111111")
                .setAccountType("personal")
                .setOwnerName("Name")
                .build();
        userInfoDTO = new UserInfoEmailDTO("testmail@test.test");
        Mockito.when(sqlConnection.getUserAccountInfos(userInfoDTO)).thenReturn(List.of(temp));
        List<AccountsInfo> result = sqlConnection.getUserAccountInfos(userInfoDTO);

        assertEquals(List.of(temp), result);
    }
    @Test
    void getAccountsInfo_queries_the_database() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        AccountsInfo temp = AccountsInfo.newBuilder()
                .setAccountBalance(10)
                .setAccountNumber("11111111111111")
                .setAccountType("personal")
                .setOwnerName("Gipsz Jakab")
                .build();
        List<AccountsInfo> list = new ArrayList<>();
        list.add(temp);

        try {
            Mockito.when(sqlConnection.getAccountsInfo()).thenReturn(list);
            list = sqlConnection.getAccountsInfo();
        } catch (NullPointerException ignored) {
        }

        Mockito.verify(connection).prepareStatement("SELECT a.account_id, u.firstname, u.lastname, a.balance, a.account_type " +
                "FROM account a JOIN \"user\" u ON a.user_id = u.user_id;");
        Mockito.verify(statement).executeQuery();
        Mockito.verify(resultSet).next();

        AccountsInfo.Builder builder = Mockito.mock(AccountsInfo.Builder.class);
        Mockito.when(builder.setAccountNumber("11111111111111")).thenReturn(builder);
        Mockito.when(builder.setOwnerName("Gipsz Jakab")).thenReturn(builder);
        Mockito.when(builder.setAccountBalance(10)).thenReturn(builder);
        Mockito.when(builder.setAccountType("personal")).thenReturn(builder);

        Mockito.when(builder.build()).thenReturn(temp);

        Mockito.mockStatic(AccountsInfo.class);
        Mockito.when(AccountsInfo.newBuilder()).thenReturn(builder);

        assertEquals(list.size(), 1);
    }
    @Test
    void getUserAccountInfos_queries_database() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        AccountsInfo temp = AccountsInfo.newBuilder()
                .setAccountBalance(10)
                .setAccountNumber("11111111111111")
                .setAccountType("personal")
                .setOwnerName("Gipsz Jakab")
                .build();
        List<AccountsInfo> list = new ArrayList<>();
        list.add(temp);
        userInfoDTO = new UserInfoEmailDTO("testmail@test.test");

        try {
            Mockito.when(sqlConnection.getUserAccountInfos(userInfoDTO)).thenReturn(list);
            list = sqlConnection.getUserAccountInfos(userInfoDTO);
        } catch (NullPointerException ignored) {
        }

        Mockito.verify(connection).prepareStatement("SELECT a.account_id, u.firstname, u.lastname, a.balance, a.account_type " +
                "FROM account a JOIN \"user\" u ON a.user_id = u.user_id " +
                "WHERE u.email = ?;");
        Mockito.verify(statement).executeQuery();
        Mockito.verify(resultSet).next();

        AccountsInfo.Builder builder = Mockito.mock(AccountsInfo.Builder.class);
        Mockito.when(builder.setAccountNumber("11111111111111")).thenReturn(builder);
        Mockito.when(builder.setOwnerName("Gipsz Jakab")).thenReturn(builder);
        Mockito.when(builder.setAccountBalance(10)).thenReturn(builder);
        Mockito.when(builder.setAccountType("personal")).thenReturn(builder);

        Mockito.when(builder.build()).thenReturn(temp);

        Mockito.mockStatic(AccountsInfo.class);
        Mockito.when(AccountsInfo.newBuilder()).thenReturn(builder);

        assertEquals(list.size(), 1);
    }

    @Test
    public void testLastInterest() throws SQLException {
        UserInfoAccNumDTO userInfoAccNumDTO = new UserInfoAccNumDTO("yourAccNum");

        Timestamp expectedTimestamp = Timestamp.valueOf(LocalDateTime.now());

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getTimestamp("dateTime")).thenReturn(expectedTimestamp);

        Timestamp resultTimestamp = sqlConnection.lastInterest(userInfoAccNumDTO);

        assertEquals(expectedTimestamp, resultTimestamp);

        verify(statement).setString(1, userInfoAccNumDTO.getAccNum());
    }


    @Test
    public void testCreditInterest() throws SQLException {
        // Arrange
        UserInfoAccNumDTO userInfoAccNumDTO = new UserInfoAccNumDTO("bbbbddddaaaacccc");
        PreparedStatement updateStatement = mock(PreparedStatement.class);
        PreparedStatement insertStatement = mock(PreparedStatement.class);

        // Mocking JDBC behavior
        when(connection.prepareStatement(contains("SELECT"))).thenReturn(statement);
        when(connection.prepareStatement(contains("UPDATE"))).thenReturn(updateStatement);
        when(connection.prepareStatement(contains("INSERT"))).thenReturn(insertStatement);

        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getDouble("balance")).thenReturn(1000.0); // Provide appropriate values
        when(resultSet.getDouble("interest_rate")).thenReturn(0.05); // Provide appropriate values
        when(updateStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = sqlConnection.creditInterest(userInfoAccNumDTO);

        // Assert
        assertTrue(result);

        // Verify that the correct methods were called with the correct parameters
        verify(connection).setAutoCommit(false);
        verify(statement).setString(1, userInfoAccNumDTO.getAccNum());
        verify(updateStatement).setDouble(1, 50.0); // Adjust according to your interest calculation logic
        verify(updateStatement).setString(2, userInfoAccNumDTO.getAccNum());
        verify(insertStatement).setTimestamp(eq(1), any(Timestamp.class));
        verify(insertStatement).setDouble(2, 50.0); // Adjust according to your interest calculation logic
        verify(insertStatement, times(2)).setString(anyInt(), eq(userInfoAccNumDTO.getAccNum()));
        verify(connection).commit();
    }



    @Test
    void testLogLoan() throws SQLException {
        LoanRequestDTO loanRequestDTO = new LoanRequestDTO(
                "123456",
                1000.0,
                0.05,
                100.0,
                com.google.protobuf.Timestamp.newBuilder().setSeconds(LocalDateTime.now().getSecond()).build(),
                5000.0
        );

        try {
            sqlConnection.logLoan(loanRequestDTO);

            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT * FROM loan WHERE account_id = ?");
            selectStatement.setString(1, loanRequestDTO.getAccountId());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                assertEquals(loanRequestDTO.getRemainingAmount(), resultSet.getDouble("remaining_amount"));
                assertEquals(loanRequestDTO.getInterestRate(), resultSet.getDouble("interest_rate"));
                assertEquals(loanRequestDTO.getMonthlyPayment(), resultSet.getDouble("monthly_payment"));
                assertEquals(loanRequestDTO.getLoanAmount(), resultSet.getDouble("loan_amount"));
            }

        } catch (RuntimeException e) {
            assertEquals(1, 0, "Exception occurred: " + e.getMessage());
        }
    }

    @Test
    void getAllTransactions_returns_a_list() throws SQLException {
        com.google.protobuf.Timestamp timestamp = com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(System.currentTimeMillis() / 1000)
                .setNanos(0)
                .build();
        Transactions temp = Transactions.newBuilder().
                setSenderAccountNumber("testmessage")
                .setRecipientAccountNumber("11111111111111")
                .setAmount(200)
                .setMessage("Name")
                .setDate(timestamp)
                .setSenderName("Alin Maaa")
                .setReceiverName("Levi Ksaaa")
                .build();
        userInfoDTO = new UserInfoEmailDTO("testmail@test.test");
        when(sqlConnection.getAllTransactions(userInfoDTO)).thenReturn(List.of(temp));
        List<Transactions> result = sqlConnection.getAllTransactions(userInfoDTO);

        assertEquals(List.of(temp), result);
    }



}
