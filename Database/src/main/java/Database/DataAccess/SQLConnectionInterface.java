package Database.DataAccess;

import Database.AccountsInfo;
import Database.DTOs.*;
import Database.Transactions;
import Database.User;
import Database.*;
import Database.DTOs.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface SQLConnectionInterface {
    void transfer(UpdatedBalancesForTransferDTO updatedBalancesForTransferDTO) throws SQLException;

    double checkBalance(CheckAccountDTO checkAccountDTO) throws SQLException;
    double checkInterestRate(CheckAccountDTO checkAccountDTO) throws SQLException;

    String checkAccountId(CheckAccountDTO checkAccountDTO) throws SQLException;

    double dailyCheck(CheckAccountDTO checkAccountDTO) throws SQLException;

    void deposit(DepositRequestDTO depositRequestDTO) throws SQLException;

    List<User> getUsers() throws SQLException;

    List<AccountsInfo> getAccountsInfo() throws SQLException;

    List<AccountsInfo> getUserAccountInfos(UserInfoEmailDTO userInfoDTO) throws SQLException;

    boolean creditInterest(CreditInterestDTO creditInterestDTO) throws SQLException;

    Timestamp lastInterest(UserInfoAccNumDTO userInfoAccNumDTO) throws SQLException;

    void logLoan(LoanRequestDTO loanRequestDTO) throws SQLException;

    List<Transactions> getAllTransactions(UserInfoEmailDTO userInfoEmailDTO);

    List<Transactions> getAllTransactionsForEmployee();

    void flagUser(FlagUserDTO flagUserDTO);

    List<Transactions> getAllSubscriptions(UserInfoEmailDTO userInfoEmailDTO);

    void registerUser(RegisterRequestDTO registerUserDTO) throws SQLException;

    int getUserID(UserAccountRequestDTO userAccountRequestDTO) throws SQLException;

    void generateAccountNumber(UserAccountDTO userAccountDTO) throws SQLException;

    String getUserEmail(UserAccountRequestDTO userAccountRequestDTO) throws SQLException;

    void updateNewBaseRate(AccountNewBaseRateDTO accountNewBaseRateDTO) throws SQLException;

    void updateUserInformation(UserNewDetailsRequestDTO userNewDetailsRequestDTO) throws SQLException;

    void createIssue(IssueCreationDTO issueDTO) throws SQLException;

    void updateIssue(IssueUpdateDTO issueDTO) throws SQLException;

    void sendMessage(MessageDTO messageDTO) throws SQLException;

    List<MessageInfo> getMessagesForIssue(IssueinfoDTO issueinfoDTO) throws SQLException;

    List<Issue> getAllIssues() throws SQLException;

    List<MessageInfo> getMessagesByIssueId(IssueinfoDTO issueinfoDTO) throws SQLException;

    void updateEmail(UserNewEmailDTO userNewEmailDTO) throws SQLException;

    void updatePassword(UserNewPasswordDTO userNewPasswordDTO) throws SQLException;

    void updatePlan(UserNewPlanDTO userNewPlanDTO) throws SQLException;
}
