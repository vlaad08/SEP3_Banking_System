package Database.DAOs;
import Database.DAOs.Interfaces.TransactionDaoInterface;
import Database.DTOs.CheckAccountDTO;
import Database.DTOs.DepositRequestDTO;
import Database.DTOs.TransferRequestDTO;
import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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

    @BeforeEach
    void setup()
    {
        dao = new TransactionDao();
        connection = Mockito.spy(SQLConnection.class);
        transferRequestDTO = Mockito.mock(TransferRequestDTO.class);
        checkAccountDTO = Mockito.mock(CheckAccountDTO.class);
        depositRequestDTO = Mockito.mock(DepositRequestDTO.class);
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
}
