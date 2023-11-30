package Database.DAOs.Interfaces;

import Database.DTOs.CheckAccountDTO;
import Database.DTOs.DepositRequestDTO;
import Database.DTOs.TransferRequestDTO;
import Database.DTOs.UserInfoAccNumDTO;
import Database.DailyCheckRequest;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface TransactionDaoInterface {
    void makeTransfer(TransferRequestDTO transferRequestDTO);
    String checkAccountId(CheckAccountDTO checkAccountDTO) throws SQLException;
    double checkBalance(CheckAccountDTO checkAccountDTO) throws SQLException;
    double dailyCheck(CheckAccountDTO checkAccountDTO) throws SQLException;
    void makeDeposit(DepositRequestDTO depositRequestDTO) throws SQLException;
    boolean creditInterest(UserInfoAccNumDTO userInfoAccNumDTO) throws SQLException;
    Timestamp lastInterest(UserInfoAccNumDTO userInfoAccNumDTO) throws SQLException;
}
