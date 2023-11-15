package Database.DAOs.Interfaces;

import Database.DTOs.CheckAccountIdDTO;
import Database.DTOs.TransferRequestDTO;
import Database.TransferRequest;

import java.sql.SQLException;

public interface TransactionDaoInterface {
    void makeTransfer(TransferRequestDTO transferRequestDTO);
    String checkAccountId(CheckAccountIdDTO checkAccountIdDTO) throws SQLException;
}
