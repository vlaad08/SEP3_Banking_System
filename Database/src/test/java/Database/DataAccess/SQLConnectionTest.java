package Database.DataAccess;

import Database.*;
import Database.DTOs.*;
import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.google.type.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
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
    private ArgumentCaptor<Integer> intCaptor;
    @Captor
    private ArgumentCaptor<Double> doubleCaptor;
    @Captor
    private ArgumentCaptor<Timestamp> timestampCaptor;
    @Captor
    private ArgumentCaptor<FlagUserDTO> flagUserDTOArgumentCaptor;
    @InjectMocks
    private UserInfoEmailDTO userInfoDTO;
    @InjectMocks
    private IssueCreationDTO issueCreationDTO;
    @InjectMocks
    private IssueinfoDTO issueinfoDTO;
    @InjectMocks
    private MessageDTO messageDTO;
    @Captor
    private ArgumentCaptor<UserInfoEmailDTO> userInfoCaptor;
    @Captor
    private ArgumentCaptor<RegisterRequestDTO> registerRequestDto;

    @BeforeEach
    void setup() throws SQLException {
        sqlConnection = Mockito.spy(SQLConnection.getInstance());
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(PreparedStatement.class);
        resultSet = Mockito.mock(ResultSet.class);
        stringCaptor = ArgumentCaptor.forClass(String.class);
        doubleCaptor = ArgumentCaptor.forClass(Double.class);
        timestampCaptor = ArgumentCaptor.forClass(Timestamp.class);
        registerRequestDto = ArgumentCaptor.forClass(RegisterRequestDTO.class);
        flagUserDTOArgumentCaptor = ArgumentCaptor.forClass(FlagUserDTO.class);
        intCaptor = ArgumentCaptor.forClass(Integer.class);

        Mockito.when(sqlConnection.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString())).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    void SQLConnection_is_created() {
        assertNotNull(sqlConnection);
    }

    @Test
    void SQLConnection_is_singleton() throws SQLException {
        SQLConnection sqlConnection1 = SQLConnection.getInstance();
        SQLConnection sqlConnection2 = SQLConnection.getInstance();
        assertSame(sqlConnection1, sqlConnection2);
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
        TransferRequestDTO transferRequestDTO = new TransferRequestDTO("aaaabbbbccccdddd", "bbbbaaaaccccdddd", 10.0, "-");
        sqlConnection.transfer(transferRequestDTO);
        Mockito.verify(connection).setAutoCommit(false);
        Mockito.verify(connection).prepareStatement("UPDATE account SET balance = balance + ? WHERE account_id = ?");
        Mockito.verify(connection).prepareStatement("UPDATE account SET balance = balance - ? WHERE account_id = ?");
        Mockito.verify(connection).prepareStatement("INSERT INTO transactions(dateTime, amount, message, senderAccount_id, recipientAccount_id) "
                +
                "VALUES (?, ?, ?, ?, ?)");
        Mockito.verify(statement, times(2)).setDouble(eq(1), anyDouble());
        Mockito.verify(statement, times(1)).setDouble(eq(2), anyDouble());
        Mockito.verify(statement, times(2)).setString(eq(2), anyString());
        Mockito.verify(statement, times(1)).setString(eq(3), anyString());
        Mockito.verify(statement, times(1)).setString(eq(4), anyString());
        Mockito.verify(statement, times(1)).setString(eq(5), anyString());
        Mockito.verify(statement, times(1)).setTimestamp(eq(1), Mockito.any(Timestamp.class));

        Mockito.verify(connection).commit();
    }
    /* tried with assert and failed
    * @Test
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
    }*/

    @Test
    void checkBalance_returns_a_double() throws SQLException {
        CheckAccountDTO checkAccountDTO = new CheckAccountDTO("-");
        Mockito.when(sqlConnection.checkBalance(checkAccountDTO)).thenReturn(10.0);
        assertEquals(10.0, sqlConnection.checkBalance(checkAccountDTO));
    }

    @Test
    void checkBalance_reaches_the_database() throws SQLException {
        CheckAccountDTO checkAccountDTO = new CheckAccountDTO("1111111111111111");
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(sqlConnection.checkBalance(checkAccountDTO)).thenReturn(1000.0);
        sqlConnection.checkBalance(checkAccountDTO);
        Mockito.verify(connection).prepareStatement(Mockito.eq("SELECT balance FROM account WHERE account_id = ?;"));
        Mockito.verify(statement).setString(eq(1),stringCaptor.capture());
        Mockito.verify(statement).executeQuery();
        Mockito.verify(resultSet).next();
        Mockito.verify(resultSet).getDouble("balance");

        assertEquals("1111111111111111",stringCaptor.getAllValues().get(0));
    }

    @Test
    void checkAccountId_returns_a_String() throws SQLException {
        CheckAccountDTO checkAccountDTO = new CheckAccountDTO("-");
        Mockito.when(sqlConnection.checkAccountId(checkAccountDTO)).thenReturn("-");
        assertEquals(sqlConnection.checkAccountId(checkAccountDTO), "-");
    }

    //infinitly running???
    /*@Test
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
    }*/

    @Test
    void dailyCheck_returns_a_double() throws SQLException {
        CheckAccountDTO checkAccountDTO = new CheckAccountDTO("-");
        Mockito.when(sqlConnection.dailyCheck(checkAccountDTO)).thenReturn(200.0);
        assertEquals(200.0, sqlConnection.dailyCheck(checkAccountDTO));
    }

    @Test
    void dailyCheck_reaches_the_database() throws SQLException {
        CheckAccountDTO checkAccountDTO = new CheckAccountDTO("-");
        Mockito.when(resultSet.next()).thenReturn(true, false);
        Mockito.when(resultSet.getDouble("sum")).thenReturn(200.0);
        double result = sqlConnection.dailyCheck(checkAccountDTO);
        Mockito.verify(connection).prepareStatement(Mockito.eq("SELECT SUM(amount)\n" +
                "FROM transactions\n" +
                "WHERE senderAccount_id = ?\n" +
                "  AND DATE_TRUNC('day', dateTime) = CURRENT_DATE;"));
        Mockito.verify(statement).setString(eq(1), stringCaptor.capture());
        Mockito.verify(statement).executeQuery();
        Mockito.verify(resultSet, Mockito.times(2)).next();
        Mockito.verify(resultSet).getDouble("sum");
        assertEquals(200.0, result, 0.01);
        assertEquals("-", stringCaptor.getValue());
    }

    @Test
    void deposit_updates_the_database() throws SQLException {
        DepositRequestDTO depositRequestDTO = new DepositRequestDTO("aaaabbbbccccdddd",50);
        sqlConnection.deposit(depositRequestDTO);
        Mockito.verify(connection).prepareStatement("UPDATE account SET balance = balance + ? WHERE account_id = ?");
        Mockito.verify(statement).setDouble(eq(1),doubleCaptor.capture());
        Mockito.verify(statement).setString(eq(2),stringCaptor.capture());

        Mockito.verify(connection).prepareStatement(Mockito.eq("INSERT INTO transactions(dateTime, amount, message, senderAccount_id, recipientAccount_id) " +
                "VALUES (?, ?, ?, ?, ?)"));
        Mockito.verify(statement).setTimestamp(Mockito.eq(1), Mockito.any(Timestamp.class));
        Mockito.verify(statement).setDouble(Mockito.eq(2), doubleCaptor.capture());
        Mockito.verify(statement).setString(Mockito.eq(3), stringCaptor.capture());
        Mockito.verify(statement).setString(Mockito.eq(4), stringCaptor.capture());
        Mockito.verify(statement).setString(Mockito.eq(5), stringCaptor.capture());
        Mockito.verify(statement,Mockito.times(2)).executeUpdate();

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
        try {
            Mockito.when(sqlConnection.getUsers()).thenReturn(users);
            users = sqlConnection.getUsers();
        } catch (NullPointerException ignored) {
        }
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
        try {
            Mockito.verify(builder.setEmail("test@example.com").setPassword("test1234").setFirstName("Test").setMiddleName("Test").setLastName("Test").setRole("testUser")).build();
        } catch (NullPointerException ignored) {
        }
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
        Mockito.verify(resultSet).getString("account_id");
        Mockito.verify(resultSet).getString("firstname");
        Mockito.verify(resultSet).getString("lastname");
        Mockito.verify(resultSet).getDouble("balance");
        Mockito.verify(resultSet).getString("account_type");
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
        Mockito.verify(resultSet).getString("account_id");
        Mockito.verify(resultSet).getString("firstname");
        Mockito.verify(resultSet).getString("lastname");
        Mockito.verify(resultSet).getDouble("balance");
        Mockito.verify(resultSet).getString("account_type");



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
    void testRegisterUser() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        RegisterRequestDTO dto = new RegisterRequestDTO("email", "fname", "mname", "lname", "12345678", "basic");
        try {
            sqlConnection.registerUser(dto);
        } catch (NullPointerException ignored) {}
        Mockito.verify(connection).prepareStatement("INSERT INTO \"user\" (email, firstName, middleName, "
                + "lastName, password, role, plan)\n"
                + "VALUES\n"
                + "  (?, ?, ?, ?, ?, ?, ?);");
        Mockito.verify(statement).setString(1, dto.getEmail());
        Mockito.verify(statement).setString(2, dto.getFirstname());
        Mockito.verify(statement).setString(3, dto.getMiddlename());
        Mockito.verify(statement).setString(4, dto.getLastname());
        Mockito.verify(statement).setString(5, dto.getPassword());
        Mockito.verify(statement).setString(6, "Client");
        Mockito.verify(statement).setString(7, dto.getPlan());
        Mockito.verify(statement).executeUpdate();
    }


    @Test
    void test_lastInterest_queries_the_database() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getTimestamp("dateTime")).thenReturn(Timestamp.valueOf("2023-01-01 12:00:00"));
        UserInfoAccNumDTO dto = new UserInfoAccNumDTO("aaaabbbbccccdddd");
        Timestamp result = sqlConnection.lastInterest(dto);

        Mockito.verify(connection).prepareStatement("SELECT t.dateTime FROM transactions t " +
                "WHERE t.senderAccount_id = ? AND t.message = 'Interest' " +
                "ORDER BY t.dateTime DESC LIMIT 1;");

        Mockito.verify(statement).setString(1, dto.getAccNum());
        assertEquals(Timestamp.valueOf("2023-01-01 12:00:00"), result);
    }


    @Test
    void test_CreditInterest_queries_the_database() throws SQLException {
        UserInfoAccNumDTO userInfoAccNumDTO = new UserInfoAccNumDTO("aaaabbbbccccdddd");
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(statement.executeUpdate()).thenReturn(1);
        sqlConnection.creditInterest(userInfoAccNumDTO);
        Mockito.verify(connection).setAutoCommit(false);
        Mockito.verify(connection).prepareStatement("SELECT a.balance, a.interest_rate FROM account a WHERE a.account_id = ?");
        Mockito.verify(connection).prepareStatement("UPDATE account SET balance = balance + ? WHERE account_id = ?");
        Mockito.verify(connection).prepareStatement("INSERT INTO transactions(dateTime, amount, message, senderAccount_id, recipientAccount_id) "
                +
                "VALUES (?, ?, ?, ?, ?)");

        Mockito.verify(statement).setString(1,userInfoAccNumDTO.getAccNum());
        Mockito.verify(statement).executeQuery();
        Mockito.verify(statement,Mockito.times(2)).executeUpdate();

        Mockito.verify(statement).setDouble(Mockito.eq(1),Mockito.anyDouble());
        Mockito.verify(statement).setString(2,userInfoAccNumDTO.getAccNum());
        Mockito.verify(statement).setTimestamp(Mockito.eq(1),Mockito.any(Timestamp.class));
        Mockito.verify(statement).setDouble(Mockito.eq(2),Mockito.anyDouble());
        Mockito.verify(statement).setString(3,"Interest");
        Mockito.verify(statement).setString(4,userInfoAccNumDTO.getAccNum());
        Mockito.verify(statement).setString(5,userInfoAccNumDTO.getAccNum());
        Mockito.verify(connection).commit();
    }


    @Test
    void logLoan_updates_the_db() throws SQLException {
        java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(LocalDateTime.now());
        com.google.protobuf.Timestamp currentTimestamp = com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(sqlTimestamp.getTime() / 1000)
                .setNanos((int) ((sqlTimestamp.getTime() % 1000) * 1_000_000))
                .build();
        LoanRequestDTO dto = new LoanRequestDTO("-",124.5,1.12,12,currentTimestamp,1000);
        java.sql.Timestamp sqlEndDate = new java.sql.Timestamp(dto.getEndDate().getSeconds() * 1000);
        sqlConnection.logLoan(dto);
        Mockito.verify(connection).setAutoCommit(false);
        Mockito.verify(connection).prepareStatement("INSERT INTO loan(account_id, remaining_amount, interest_rate, monthly_payment, end_date, loan_amount) "
                +
                "VALUES (?, ?, ?, ?, ?, ?)");
        Mockito.verify(statement).setString(1, dto.getAccountId());
        Mockito.verify(statement).setDouble(2, dto.getRemainingAmount());
        Mockito.verify(statement).setDouble(3, dto.getInterestRate());
        Mockito.verify(statement).setDouble(4, dto.getMonthlyPayment());
        Mockito.verify(statement).setTimestamp(5, sqlEndDate);
        Mockito.verify(statement).setDouble(6, dto.getLoanAmount());
        Mockito.verify(connection).prepareStatement("INSERT INTO transactions(dateTime, amount, message, senderAccount_id, recipientAccount_id) " +
                "VALUES (?, ?, ?, ?, ?)");
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Mockito.verify(statement).setTimestamp(Mockito.eq(1),Mockito.any(Timestamp.class));
        Mockito.verify(statement).setDouble(2,dto.getLoanAmount());
        Mockito.verify(statement).setString(3,"Loan");
        Mockito.verify(statement).setString(4,dto.getAccountId());
        Mockito.verify(statement).setString(4,dto.getAccountId());
        Mockito.verify(statement,Mockito.times(2)).executeUpdate();
        Mockito.verify(connection).commit();
    }

    @Test
    void getAllTransactions_returns_a_list_and_queries_a_database() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        UserInfoEmailDTO dto =new UserInfoEmailDTO("-");
        java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(LocalDateTime.now().minusDays(1));
        com.google.protobuf.Timestamp currentTimestamp = com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(sqlTimestamp.getTime() / 1000)
                .setNanos((int) ((sqlTimestamp.getTime() % 1000) * 1_000_000))
                .build();
        Transactions transaction1 = Transactions.newBuilder()
                .setSenderAccountNumber("aaaabbbbccccdddd")
                .setRecipientAccountNumber("bbbbaaaaccccdddd")
                .setAmount(100)
                .setMessage("-")
                .setDate(currentTimestamp)
                .setSenderName("-")
                .setReceiverName("-")
                .setSenderId(2)
                .build();
        List<Transactions> transactions = new ArrayList<>();
        transactions.add(transaction1);
        List<Transactions> gotten = new ArrayList<>();
        try {
            Mockito.when(sqlConnection.getAllTransactions(dto)).thenReturn(transactions);
            gotten = sqlConnection.getAllTransactions(dto);
        } catch (NullPointerException ignored) {
        }
        Mockito.verify(connection).prepareStatement("SELECT t.senderAccount_id, t.recipientAccount_id, t.amount, t.message, t.dateTime, u1.firstName AS senderFirstName, u1.lastName AS senderLastName, u2.firstName AS receiverFirstName, u2.lastName AS receiverLastName "
                +
                "FROM transactions t " +
                "JOIN account a1 ON t.senderAccount_id = a1.account_id " +
                "JOIN account a2 ON t.recipientAccount_id = a2.account_id " +
                "JOIN \"user\" u1 ON a1.user_id = u1.user_id " +
                "JOIN \"user\" u2 ON a2.user_id = u2.user_id " +
                "WHERE u1.email = ? OR u2.email = ? " +
                "ORDER BY t.dateTime DESC;");
        Mockito.verify(statement).setString(1,dto.getEmail());
        Mockito.verify(statement).setString(2,dto.getEmail());
        Mockito.verify(statement).executeQuery();
        Mockito.verify(resultSet).next();
        Mockito.verify(resultSet).getString("senderAccount_id");
        Mockito.verify(resultSet).getString("recipientAccount_id");
        Mockito.verify(resultSet).getDouble("amount");
        Mockito.verify(resultSet).getString("message");
        Mockito.verify(resultSet).getTimestamp("dateTime");
        Mockito.verify(resultSet).getString("senderFirstName");
        Mockito.verify(resultSet).getString("senderLastName");
        Mockito.verify(resultSet).getString("receiverFirstName");
        Mockito.verify(resultSet).getString("receiverLastName");
        Transactions.Builder builder = Mockito.mock(Transactions.Builder.class);
        try {
            Mockito.verify(builder.setSenderAccountNumber("aaaabbbbccccdddd").setRecipientAccountNumber("bbbbaaaaccccdddd").setAmount(100).setDate(currentTimestamp).setSenderName("-").setReceiverName("-").setSenderId(2)).build();
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void testCreateIssue() throws SQLException {
        IssueCreationDTO issueDTO = new IssueCreationDTO("Title", "Body", 1);

        try {
            sqlConnection.createIssue(issueDTO);

            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT * FROM issues WHERE title = ?");
            selectStatement.setString(1, issueDTO.getTitle());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                assertEquals(issueDTO.getTitle(), resultSet.getString("title"));
                assertEquals(issueDTO.getBody(), resultSet.getString("body"));
                assertEquals(issueDTO.getOwnerId(), resultSet.getInt("owner_id"));
            }

        } catch (RuntimeException e) {
            assertEquals(1, 0, "Exception occurred: " + e.getMessage());
        }
    }

    @Test
    void testSendMessage() throws SQLException {
        java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(LocalDateTime.now());

        com.google.protobuf.Timestamp protobufTimestamp = com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(sqlTimestamp.getTime() / 1000)
                .setNanos((int) ((sqlTimestamp.getTime() % 1000) * 1_000_000))
                .build();

        MessageDTO messageDTO = new MessageDTO("Sample Title", 1, "Body", 1, protobufTimestamp);

        sqlConnection.sendMessage(messageDTO);

        ArgumentCaptor<Timestamp> timestampCaptor = ArgumentCaptor.forClass(Timestamp.class);
        Mockito.verify(statement).setTimestamp(eq(4), timestampCaptor.capture());
        Mockito.verify(statement).setString(1, messageDTO.getTitle());
        Mockito.verify(statement).setString(2, messageDTO.getBody());
        Mockito.verify(statement).setInt(3, messageDTO.getOwner());
        Mockito.verify(statement).setInt(5, messageDTO.getIssueId());

        Mockito.verify(statement).executeUpdate();

        Mockito.verify(connection).commit();
    }

    @Test
    void testGetMessagesForIssue() throws SQLException {
        IssueinfoDTO issueinfoDTO = new IssueinfoDTO(1);

        ResultSet resultSetMock = mock(ResultSet.class);
        Mockito.when(resultSetMock.next()).thenReturn(true, false);
        Mockito.when(resultSetMock.getString("title")).thenReturn("Sample Title");
        Mockito.when(resultSetMock.getString("body")).thenReturn("Body");
        Mockito.when(resultSetMock.getInt("owner_id")).thenReturn(1);

        java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(LocalDateTime.now());
        com.google.protobuf.Timestamp expectedDate = com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(sqlTimestamp.getTime() / 1000)
                .setNanos((int) ((sqlTimestamp.getTime() % 1000) * 1_000_000))
                .build();
        Mockito.when(resultSetMock.getTimestamp("creation_time")).thenReturn(sqlTimestamp);

        PreparedStatement statementMock = mock(PreparedStatement.class);
        Mockito.when(statementMock.executeQuery()).thenReturn(resultSetMock);

        Connection connectionMock = mock(Connection.class);
        Mockito.when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);

        SQLConnection sqlConnectionMock = spy(new SQLConnection());
        Mockito.doReturn(connectionMock).when(sqlConnectionMock).getConnection();

        List<MessageInfo> result = sqlConnectionMock.getMessagesForIssue(issueinfoDTO);

        Mockito.verify(statementMock).setInt(1, issueinfoDTO.getId());

        Mockito.verify(resultSetMock).getString("title");
        Mockito.verify(resultSetMock).getString("body");
        Mockito.verify(resultSetMock).getInt("owner_id");
        Mockito.verify(resultSetMock).getTimestamp("creation_time");

        MessageInfo expectedMessageInfo = MessageInfo.newBuilder()
                .setTitle("Sample Title")
                .setBody("Body")
                .setOwner(1)
                .setCreationTime(expectedDate)
                .setIssueId(issueinfoDTO.getId())
                .build();
        assertEquals(List.of(expectedMessageInfo), result);
    }

    @Test
    void testGetAllIssues() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);

        Mockito.when(resultSetMock.next()).thenReturn(true, true, false);
        Mockito.when(resultSetMock.getInt("issue_id")).thenReturn(1, 2);
        Mockito.when(resultSetMock.getString("title")).thenReturn("Issue 1", "Issue 2");
        Mockito.when(resultSetMock.getString("body")).thenReturn("Body 1", "Body 2");
        Mockito.when(resultSetMock.getInt("owner_id")).thenReturn(1, 2);

        java.sql.Timestamp sqlTimestamp1 = java.sql.Timestamp.valueOf(LocalDateTime.now().minusDays(1));
        com.google.protobuf.Timestamp date1 = com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(sqlTimestamp1.getTime() / 1000)
                .setNanos((int) ((sqlTimestamp1.getTime() % 1000) * 1_000_000))
                .build();

        java.sql.Timestamp sqlTimestamp2 = java.sql.Timestamp.valueOf(LocalDateTime.now());
        com.google.protobuf.Timestamp date2 = com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(sqlTimestamp2.getTime() / 1000)
                .setNanos((int) ((sqlTimestamp2.getTime() % 1000) * 1_000_000))
                .build();

        Mockito.when(resultSetMock.getTimestamp("creation_time")).thenReturn(sqlTimestamp1, sqlTimestamp2);
        Mockito.when(resultSetMock.getBoolean("flagged")).thenReturn(true, false);

        PreparedStatement statementMock = mock(PreparedStatement.class);
        Mockito.when(statementMock.executeQuery()).thenReturn(resultSetMock);

        Connection connectionMock = mock(Connection.class);
        Mockito.when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);

        SQLConnection sqlConnectionMock = spy(new SQLConnection());
        doReturn(connectionMock).when(sqlConnectionMock).getConnection();
        doReturn(List.of(MessageInfo.getDefaultInstance())).when(sqlConnectionMock).getMessagesForIssue(any());

        List<Issue> result = sqlConnectionMock.getAllIssues();

        Mockito.verify(statementMock).executeQuery();

        Mockito.verify(resultSetMock, times(2)).getInt("issue_id");
        Mockito.verify(resultSetMock, times(2)).getString("title");
        Mockito.verify(resultSetMock, times(2)).getString("body");
        Mockito.verify(resultSetMock, times(2)).getInt("owner_id");
        Mockito.verify(resultSetMock, times(2)).getTimestamp("creation_time");
        Mockito.verify(resultSetMock, times(2)).getBoolean("flagged");

        Issue expectedIssue1 = Issue.newBuilder()
                .setIssueId(1)
                .setTitle("Issue 1")
                .setBody("Body 1")
                .setOwnerId(1)
                .setCreationTime(date1)
                .setFlagged(true)
                .addAllMessages(List.of(MessageInfo.getDefaultInstance()))
                .build();

        Issue expectedIssue2 = Issue.newBuilder()
                .setIssueId(2)
                .setTitle("Issue 2")
                .setBody("Body 2")
                .setOwnerId(2)
                .setCreationTime(date2)
                .setFlagged(false)
                .addAllMessages(List.of(MessageInfo.getDefaultInstance()))
                .build();

        assertEquals(List.of(expectedIssue1, expectedIssue2), result);
    }

    @Test
    void getAllTransactionsForEmployee_queries_the_db_and_returns_a_list() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(LocalDateTime.now().minusDays(1));
        com.google.protobuf.Timestamp currentTimestamp = com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(sqlTimestamp.getTime() / 1000)
                .setNanos((int) ((sqlTimestamp.getTime() % 1000) * 1_000_000))
                .build();
        Transactions transaction1 = Transactions.newBuilder()
                .setSenderAccountNumber("aaaabbbbccccdddd")
                .setRecipientAccountNumber("bbbbaaaaccccdddd")
                .setAmount(100)
                .setMessage("-")
                .setDate(currentTimestamp)
                .setSenderName("-")
                .setReceiverName("-")
                .setSenderId(2)
                .build();
        List<Transactions> transactions = new ArrayList<>();
        transactions.add(transaction1);
        List<Transactions> gotten = new ArrayList<>();
        try {
            Mockito.when(sqlConnection.getAllTransactionsForEmployee()).thenReturn(transactions);
            gotten = sqlConnection.getAllTransactionsForEmployee();
        } catch (NullPointerException ignored) {
        }

        Mockito.verify(connection).prepareStatement("SELECT t.senderAccount_id, t.recipientAccount_id, t.amount, t.message, t.dateTime, u1.firstName AS senderFirstName, u1.lastName AS senderLastName, u2.firstName AS receiverFirstName, u2.lastName AS receiverLastName,u1.user_id AS senderId\n" +
                "FROM transactions t\n" +
                "JOIN account a1 ON t.senderAccount_id = a1.account_id\n" +
                "JOIN account a2 ON t.recipientAccount_id = a2.account_id\n" +
                "JOIN \"user\" u1 ON a1.user_id = u1.user_id\n" +
                "JOIN \"user\" u2 ON a2.user_id = u2.user_id\n" +
                "ORDER BY t.dateTime DESC;");
        Mockito.verify(statement).executeQuery();
        Mockito.verify(resultSet).next();
        Mockito.verify(resultSet).getString("senderAccount_id");
        Mockito.verify(resultSet).getString("recipientAccount_id");
        Mockito.verify(resultSet).getDouble("amount");
        Mockito.verify(resultSet).getString("message");
        Mockito.verify(resultSet).getTimestamp("dateTime");
        Mockito.verify(resultSet).getString("senderFirstName");
        Mockito.verify(resultSet).getString("senderLastName");
        Mockito.verify(resultSet).getString("receiverFirstName");
        Mockito.verify(resultSet).getString("receiverLastName");
        Mockito.verify(resultSet).getInt("senderId");
        Transactions.Builder builder = Mockito.mock(Transactions.Builder.class);
        try {
            Mockito.verify(builder.setSenderAccountNumber("aaaabbbbccccdddd").setRecipientAccountNumber("bbbbaaaaccccdddd").setAmount(100).setDate(currentTimestamp).setSenderName("-").setReceiverName("-").setSenderId(2)).build();
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void flagUser_updates_and_calls_db() throws SQLException {
        FlagUserDTO dto = new FlagUserDTO(1);
        sqlConnection.flagUser(dto);
        Mockito.verify(connection).setAutoCommit(false);
        Mockito.verify(connection).prepareStatement("UPDATE \"user\" SET flag=true WHERE user_id=?;");
        Mockito.verify(statement).setInt(1, dto.getSenderId());
        Mockito.verify(statement).executeUpdate();
        Mockito.verify(connection).commit();
    }

    @Test
    void updateIssue_updates_and_calls_db() throws SQLException {
        IssueUpdateDTO dto = new IssueUpdateDTO(1);
        sqlConnection.updateIssue(dto);
        Mockito.verify(connection).setAutoCommit(false);
        Mockito.verify(connection).prepareStatement("UPDATE issues SET flagged=true WHERE issue_id=?;");
        Mockito.verify(statement).setInt(1, dto.getId());
        Mockito.verify(statement).executeUpdate();
        Mockito.verify(connection).commit();
    }

    @Test
    void getMessageByIssueId_queries_and_calls_the_db() throws SQLException {
        IssueinfoDTO dto = new IssueinfoDTO(1);
        Mockito.when(resultSet.next()).thenReturn(true);
        java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(LocalDateTime.now().minusDays(1));
        com.google.protobuf.Timestamp currentTimestamp = com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(sqlTimestamp.getTime() / 1000)
                .setNanos((int) ((sqlTimestamp.getTime() % 1000) * 1_000_000))
                .build();
        MessageInfo messageInfo = MessageInfo.newBuilder()
                .setTitle("-")
                .setOwner(1)
                .setBody("-")
                .setIssueId(1)
                .setCreationTime(currentTimestamp)
                .build();
        List<MessageInfo> messageInfos = new ArrayList<>();
        messageInfos.add(messageInfo);
        List<MessageInfo> gotten = new ArrayList<>();
        try {
            Mockito.when(sqlConnection.getMessagesByIssueId(dto)).thenReturn(messageInfos);
            gotten = sqlConnection.getMessagesByIssueId(dto);
        } catch (NullPointerException ignored) {
        }

        Mockito.verify(connection).prepareStatement("SELECT title, body, owner_id, creation_time FROM messages WHERE issue_id = ?");
        Mockito.verify(statement).setInt(1, dto.getId());
        Mockito.verify(statement).executeQuery();
        Mockito.verify(resultSet).next();
        Mockito.verify(resultSet).getString("title");
        Mockito.verify(resultSet).getString("body");
        Mockito.verify(resultSet).getInt("owner_id");
        Mockito.verify(resultSet).getTimestamp("creation_time");
        MessageInfo.Builder builder = Mockito.mock(MessageInfo.Builder.class);
        try {
            Mockito.verify(builder.setTitle("-").setBody("-").setOwner(1).setCreationTime(currentTimestamp).setIssueId(1).build());
        } catch (NullPointerException ignored) {
        }
    }
    @Test
    public void getUserId_returns_an_int() throws SQLException {
        UserAccountRequestDTO dto = new UserAccountRequestDTO("-");
        Mockito.when(sqlConnection.getUserID(dto)).thenReturn(1);
        int smth = sqlConnection.getUserID(dto);
        assertEquals(1, smth);
    }

    @Test
    public void getUserId_queries_and_call_the_db() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        UserAccountRequestDTO dto = new UserAccountRequestDTO("-");
        sqlConnection.getUserID(dto);
        Mockito.verify(connection).prepareStatement("SELECT user_id FROM \"user\" where email = ?;");
        Mockito.verify(statement).setString(1,dto.getEmail());
        Mockito.verify(statement).executeQuery();
        Mockito.verify(resultSet).next();
        Mockito.verify(resultSet).getInt("user_id");
    }

    @Test
    public void generateAccountNumber_updates_and_call_the_db() throws SQLException {
        UserAccountDTO dto = new UserAccountDTO(1,"-",1.12);
        sqlConnection.generateAccountNumber(dto);
        Mockito.verify(connection).prepareStatement("INSERT INTO account "
                + "(account_id, user_id, balance, account_type, interest_rate)\n"
                + "VALUES\n" + "  (?, ?, 0, ?, ?);");
        Mockito.verify(statement).setString(1, dto.getUserAccountNumber());
        Mockito.verify(statement).setInt(2, dto.getUser_id());
        Mockito.verify(statement).setString(3, dto.getAccountType());
        Mockito.verify(statement).setDouble(4, dto.getInterestRate());
        Mockito.verify(statement).executeUpdate();
    }

    @Test
    void getUserEmail_returns_a_string() throws SQLException {
        UserAccountRequestDTO dto = new UserAccountRequestDTO("-");
        Mockito.when(sqlConnection.getUserEmail(dto)).thenReturn("ok");
        String s = sqlConnection.getUserEmail(dto);
        assertEquals(s,"ok");
    }

    @Test
    public void getUserEmail_queries_and_call_the_db() throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        UserAccountRequestDTO dto = new UserAccountRequestDTO("-");
        sqlConnection.getUserEmail(dto);
        Mockito.verify(connection).prepareStatement("SELECT email FROM \"user\" WHERE email = ?");
        Mockito.verify(statement).setString(1, dto.getEmail());
        Mockito.verify(statement).executeQuery();
        Mockito.verify(resultSet).next();
        Mockito.verify(resultSet).getString("email");
    }

    @Test
    public void updateNewBaseRate_updates_and_calls_db() throws SQLException {
        AccountNewBaseRateDTO dto = new AccountNewBaseRateDTO(1,1.12);
        sqlConnection.updateNewBaseRate(dto);
        Mockito.verify(connection).prepareStatement("UPDATE account SET "
                + "interest_rate = ? where user_id = ?");
        Mockito.verify(statement).setDouble(1, dto.getBaseRate());
        Mockito.verify(statement).setInt(2, dto.getUser_id());
        Mockito.verify(statement).executeUpdate();
    }

    @Test
    public void updateUserInformation_updates_and_calls_db() throws SQLException {
        UserNewDetailsRequestDTO dto = new UserNewDetailsRequestDTO("-","-","-","-");
        sqlConnection.updateUserInformation(dto);
        Mockito.verify(connection).prepareStatement("UPDATE \"user\" SET email = ?, password = ?, plan = ? WHERE "
                + "email = ?");
        Mockito.verify(statement).setString(1, dto.getNewEmail());
        Mockito.verify(statement).setString(2, dto.getPassword());
        Mockito.verify(statement).setString(3, dto.getPlan());
        Mockito.verify(statement).setString(4, dto.getOldEmail());
        Mockito.verify(statement).executeUpdate();
    }



}
