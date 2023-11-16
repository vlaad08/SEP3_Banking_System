package Database.DAOs;

import Database.DAOs.Interfaces.TransactionDaoInterface;
import Database.DTOs.CheckAccountIdDTO;
import Database.DTOs.TransferRequestDTO;
import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;
import Database.TransferRequest;

import java.sql.SQLException;

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
    public String checkAccountId(CheckAccountIdDTO checkAccountIdDTO) throws SQLException {
        return connection.checkAccountId(checkAccountIdDTO.getSrecipientAcoount_id());
    }


}
