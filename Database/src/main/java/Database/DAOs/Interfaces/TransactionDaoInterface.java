package Database.DAOs.Interfaces;

import Database.DTOs.CheckAccountDTO;
import Database.DTOs.TransferRequestDTO;

import java.sql.SQLException;

public interface TransactionDaoInterface {
    void makeTransfer(TransferRequestDTO transferRequestDTO);
    String checkAccountId(CheckAccountDTO checkAccountDTO) throws SQLException;
    double checkBalance(CheckAccountDTO checkAccountDTO) throws SQLException;
}
