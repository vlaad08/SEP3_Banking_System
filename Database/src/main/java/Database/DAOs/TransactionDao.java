package Database.DAOs;

import Database.DAOs.Interfaces.TransactionDaoInterface;
import Database.DTOs.CheckAccountDTO;
import Database.DTOs.TransferRequestDTO;
import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;

import java.sql.SQLException;
import java.util.List;

public class TransactionDao implements TransactionDaoInterface
{
    SQLConnectionInterface connection;

    {
        try {
            connection = SQLConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void makeTransfer(TransferRequestDTO transferRequestDTO) {
        connection.transfer(transferRequestDTO.getSenderAccount_id(),transferRequestDTO.getRecipientAccount_id(),transferRequestDTO.getAmount(),transferRequestDTO.getMessage());
    }

    @Override
    public String checkAccountId(CheckAccountDTO checkAccountIdDTO) throws SQLException {
        return connection.checkAccountId(checkAccountIdDTO.getRecipientAccount_id());
    }

    @Override
    public double checkBalance(CheckAccountDTO checkAccountDTO) throws SQLException {
        return connection.checkBalance(checkAccountDTO.getRecipientAccount_id());
    }

    @Override
    public double dailyCheck(CheckAccountDTO checkAccountDTO) throws SQLException {
        return connection.dailyCheck(checkAccountDTO.getRecipientAccount_id());
    }


}
