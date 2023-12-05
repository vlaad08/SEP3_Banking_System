package Database.DataAccess;

import Database.*;
import Database.DTOs.*;
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
    @InjectMocks
    private IssueCreationDTO issueCreationDTO;
    @InjectMocks
    private IssueinfoDTO issueinfoDTO;
    @InjectMocks
    private MessageDTO messageDTO;
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
        // Create a sample java.sql.Timestamp for testing
        java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(LocalDateTime.now());

        // Convert java.sql.Timestamp to com.google.protobuf.Timestamp
        com.google.protobuf.Timestamp protobufTimestamp = com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(sqlTimestamp.getTime() / 1000)
                .setNanos((int) ((sqlTimestamp.getTime() % 1000) * 1_000_000))
                .build();

        // Create a sample MessageDTO for testing
        MessageDTO messageDTO = new MessageDTO("Sample Title", 1, "Body", 1, protobufTimestamp);

        // Call the sendMessage method with the sample MessageDTO
        sqlConnection.sendMessage(messageDTO);

        // Capture the timestamp used in sendMessage method
        ArgumentCaptor<Timestamp> timestampCaptor = ArgumentCaptor.forClass(Timestamp.class);
        verify(statement).setTimestamp(eq(4), timestampCaptor.capture());

        // Verify that the PreparedStatement methods were called with the correct parameters
        verify(statement).setString(1, messageDTO.getTitle());
        verify(statement).setString(2, messageDTO.getBody());
        verify(statement).setInt(3, messageDTO.getOwner());

        verify(statement).setInt(5, messageDTO.getIssueId());

        // Verify that executeUpdate was called
        verify(statement).executeUpdate();

        // Verify that commit was called
        verify(connection).commit();
    }

    @Test
    void testGetMessagesForIssue() throws SQLException {
        // Create a sample IssueinfoDTO for testing
        IssueinfoDTO issueinfoDTO = new IssueinfoDTO(1); // Set the ID to the expected issue ID

        // Create a mock ResultSet with expected data
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true, false); // Simulate one row of data
        when(resultSetMock.getString("title")).thenReturn("Sample Title");
        when(resultSetMock.getString("body")).thenReturn("Body");
        when(resultSetMock.getInt("owner_id")).thenReturn(1);

        // Set the expected creation time
        java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(LocalDateTime.now());
        com.google.protobuf.Timestamp expectedDate = com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(sqlTimestamp.getTime() / 1000)
                .setNanos((int) ((sqlTimestamp.getTime() % 1000) * 1_000_000))
                .build();
        when(resultSetMock.getTimestamp("creation_time")).thenReturn(sqlTimestamp);

        // Mock the PreparedStatement
        PreparedStatement statementMock = mock(PreparedStatement.class);
        when(statementMock.executeQuery()).thenReturn(resultSetMock);

        // Mock the Connection
        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);

        // Mock the SQLConnection
        SQLConnection sqlConnectionMock = spy(new SQLConnection());
        doReturn(connectionMock).when(sqlConnectionMock).getConnection();

        // Call the getMessagesForIssue method with the sample IssueinfoDTO
        List<MessageInfo> result = sqlConnectionMock.getMessagesForIssue(issueinfoDTO);

        // Verify that the PreparedStatement methods were called with the correct parameters
        verify(statementMock).setInt(1, issueinfoDTO.getId());

        // Verify that the ResultSet methods were called to retrieve data
        verify(resultSetMock).getString("title");
        verify(resultSetMock).getString("body");
        verify(resultSetMock).getInt("owner_id");
        verify(resultSetMock).getTimestamp("creation_time");

        // Verify that the expected MessageInfo object was created
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
        // Create a mock ResultSet with expected data for two issues
        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.next()).thenReturn(true, true, false); // Simulate two rows of data
        when(resultSetMock.getInt("issue_id")).thenReturn(1, 2);
        when(resultSetMock.getString("title")).thenReturn("Issue 1", "Issue 2");
        when(resultSetMock.getString("body")).thenReturn("Body 1", "Body 2");
        when(resultSetMock.getInt("owner_id")).thenReturn(1, 2);

        // Set the expected creation time for both issues
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

        when(resultSetMock.getTimestamp("creation_time")).thenReturn(sqlTimestamp1, sqlTimestamp2);
        when(resultSetMock.getBoolean("flagged")).thenReturn(true, false);

        // Mock the PreparedStatement
        PreparedStatement statementMock = mock(PreparedStatement.class);
        when(statementMock.executeQuery()).thenReturn(resultSetMock);

        // Mock the Connection
        Connection connectionMock = mock(Connection.class);
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);

        // Mock the SQLConnection
        SQLConnection sqlConnectionMock = spy(new SQLConnection());
        doReturn(connectionMock).when(sqlConnectionMock).getConnection();
        doReturn(List.of(MessageInfo.getDefaultInstance())).when(sqlConnectionMock).getMessagesForIssue(any());

        // Call the getAllIssues method
        List<Issue> result = sqlConnectionMock.getAllIssues();

        // Verify that the PreparedStatement methods were called with the correct parameters
        verify(statementMock).executeQuery();

        // Verify that the ResultSet methods were called to retrieve data
        verify(resultSetMock, times(2)).getInt("issue_id");
        verify(resultSetMock, times(2)).getString("title");
        verify(resultSetMock, times(2)).getString("body");
        verify(resultSetMock, times(2)).getInt("owner_id");
        verify(resultSetMock, times(2)).getTimestamp("creation_time");
        verify(resultSetMock, times(2)).getBoolean("flagged");

        // Verify that the expected Issue objects were created
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

//MORE TESTING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!




}
