package Database.DAOs.Interfaces;

import Database.DTOs.*;
import Database.DailyCheckRequest;
import Database.Transactions;

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
    void logLoan(LoanRequestDTO loanRequestDTO) throws SQLException;
    List<Transactions> getAllTransactions(UserInfoEmailDTO userInfoEmailDTO) throws SQLException;
}
