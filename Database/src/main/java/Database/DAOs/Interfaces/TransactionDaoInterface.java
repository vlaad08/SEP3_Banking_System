package Database.DAOs.Interfaces;

import Database.DTOs.CheckAccountDTO;
import Database.DTOs.TransferRequestDTO;
import Database.DailyCheckRequest;

import java.sql.SQLException;
import java.util.List;

public interface TransactionDaoInterface {
    void makeTransfer(TransferRequestDTO transferRequestDTO);
    String checkAccountId(CheckAccountDTO checkAccountDTO) throws SQLException;
    double checkBalance(CheckAccountDTO checkAccountDTO) throws SQLException;

    double dailyCheck(CheckAccountDTO checkAccountDTO) throws SQLException;
}
