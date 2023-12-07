package Database.DAOs;
import Database.DAOs.Interfaces.TransactionDaoInterface;
import Database.DTOs.*;
import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionDAOTest {

    @InjectMocks
    private TransactionDaoInterface dao;
    @Mock
    private SQLConnectionInterface connection;
    private TransferRequestDTO transferRequestDTO;
    private CheckAccountDTO checkAccountDTO;
    private DepositRequestDTO depositRequestDTO;
    private UserInfoAccNumDTO userInfoAccNumDTO;
    private LoanRequestDTO loanRequestDTO;
    private UserInfoEmailDTO userInfoEmailDTO;
    private FlagUserDTO flagUserDTO;

    @BeforeEach
    void setup()
    {
        dao = new TransactionDao();
        connection = Mockito.spy(SQLConnection.class);
        transferRequestDTO = Mockito.mock(TransferRequestDTO.class);
        checkAccountDTO = Mockito.mock(CheckAccountDTO.class);
        depositRequestDTO = Mockito.mock(DepositRequestDTO.class);
        userInfoAccNumDTO = Mockito.mock(UserInfoAccNumDTO.class);
        loanRequestDTO = Mockito.mock(LoanRequestDTO.class);
        userInfoEmailDTO = Mockito.mock(UserInfoEmailDTO.class);
        flagUserDTO = Mockito.mock(FlagUserDTO.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void makeTransfer_connection_called()
    {
        dao.makeTransfer(transferRequestDTO);
        Mockito.verify(connection).transfer(transferRequestDTO.getSenderAccount_id(),transferRequestDTO.getRecipientAccount_id(),transferRequestDTO.getAmount(),transferRequestDTO.getMessage());
    }
    @Test
    void checkAccountId_connection_called() throws SQLException {
        dao.checkAccountId(checkAccountDTO);
        Mockito.verify(connection).checkAccountId(checkAccountDTO.getRecipientAccount_id());
    }

    @Test
    void checkAccountId_throws_SQLException() throws SQLException {
        Mockito.when(connection.checkAccountId(checkAccountDTO.getRecipientAccount_id())).thenThrow(SQLException.class);
        assertThrows(SQLException.class, () -> dao.checkAccountId(checkAccountDTO));
    }
    @Test
    void checkBalance_connection_called() throws SQLException {
        dao.checkBalance(checkAccountDTO);
        Mockito.verify(connection).checkBalance(checkAccountDTO.getRecipientAccount_id());
    }
    @Test
    void checkBalance_throws_SQLException() throws SQLException
    {
        Mockito.when(connection.checkBalance(checkAccountDTO.getRecipientAccount_id())).thenThrow(SQLException.class);
        assertThrows(SQLException.class, ()->dao.checkBalance(checkAccountDTO));
    }
    @Test
    void dailyCheck_connection_called() throws SQLException {
        dao.dailyCheck(checkAccountDTO);
        Mockito.verify(connection).dailyCheck(checkAccountDTO.getRecipientAccount_id());
    }
    @Test
    void dailyCheck_throws_SQLException() throws SQLException
    {
        Mockito.when(connection.dailyCheck(checkAccountDTO.getRecipientAccount_id())).thenThrow(SQLException.class);
        assertThrows(SQLException.class, ()->dao.dailyCheck(checkAccountDTO));
    }
    @Test
    void makeDeposit_connection_called() throws SQLException {
        dao.makeDeposit(depositRequestDTO);
        Mockito.verify(connection).deposit(depositRequestDTO.getAccount_id(),depositRequestDTO.getAmount());
    }

    @Test
    void creditInterest_connection_called() throws SQLException {
        dao.creditInterest(userInfoAccNumDTO);
        Mockito.verify(connection).creditInterest(userInfoAccNumDTO);
    }

    @Test
    void creditInterest_throws_SQLException() throws SQLException {
        Mockito.when(connection.creditInterest(userInfoAccNumDTO)).thenThrow(SQLException.class);
        assertThrows(SQLException.class, ()->dao.creditInterest(userInfoAccNumDTO));
    }

    @Test
    void lastInterest_connection_called() throws SQLException {
        dao.lastInterest(userInfoAccNumDTO);
        Mockito.verify(connection).lastInterest(userInfoAccNumDTO);
    }

    @Test
    void lastInterest_throws_SQLException() throws SQLException {
        Mockito.when(connection.lastInterest(userInfoAccNumDTO)).thenThrow(SQLException.class);
        assertThrows(SQLException.class, ()->dao.lastInterest(userInfoAccNumDTO));
    }

    @Test
    void logLoan_connection_called() throws SQLException {
        dao.logLoan(loanRequestDTO);
        Mockito.verify(connection).logLoan(loanRequestDTO);
    }

    @Test
    void logLoan_throws_SQLException() throws SQLException {
        Mockito.doThrow(SQLException.class).when(connection).logLoan(loanRequestDTO);
        assertThrows(SQLException.class, () -> dao.logLoan(loanRequestDTO));
    }

    @Test
    void getAllTransactions_connection_called() throws SQLException {
        dao.getAllTransactions(userInfoEmailDTO);
        Mockito.verify(connection).getAllTransactions(userInfoEmailDTO);
    }
    @Test
    void getAllTransactionsForEmployee_connection_called() throws SQLException {
        dao.getAllTransactionsForEmployee();
        Mockito.verify(connection).getAllTransactionsForEmployee();
    }

    @Test
    void flagUser_connection_called()
    {
        dao.flagUser(flagUserDTO);
        Mockito.verify(connection).flagUser(flagUserDTO);
    }
}
