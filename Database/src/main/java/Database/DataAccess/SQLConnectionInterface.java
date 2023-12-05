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
    void transfer(String id_1, String id_2, double amount, String message);

    double checkBalance(String account_id) throws SQLException;

    String checkAccountId(String account_id) throws SQLException;

    double dailyCheck(String account_id) throws SQLException;

    void deposit(String account_id, double amount) throws SQLException;

    List<User> getUsers() throws SQLException;

    List<AccountsInfo> getAccountsInfo() throws SQLException;

    List<AccountsInfo> getUserAccountInfos(UserInfoEmailDTO userInfoDTO) throws SQLException;

    boolean creditInterest(UserInfoAccNumDTO userInfoAccNumDTO) throws SQLException;

    Timestamp lastInterest(UserInfoAccNumDTO userInfoAccNumDTO) throws SQLException;

    void logLoan(LoanRequestDTO loanRequestDTO) throws SQLException;

    List<Transactions> getAllTransactions(UserInfoEmailDTO userInfoEmailDTO);

    void registerUser(RegisterRequestDTO registerUserDTO) throws SQLException;

    int getUserID(UserAccountRequestDTO userAccountRequestDTO) throws SQLException;

    void generateAccountNumber(UserAccountDTO userAccountDTO) throws SQLException;

    String getUserEmail(UserAccountRequestDTO userAccountRequestDTO) throws SQLException;

    void updateNewBaseRate(AccountNewBaseRateDTO accountNewBaseRateDTO) throws SQLException;

    void updateUserInformation(UserNewDetailsRequestDTO userNewDetailsRequestDTO) throws SQLException;

    void createIssue(IssueCreationDTO issueDTO) throws SQLException;

    void sendMessage(MessageDTO messageDTO) throws SQLException;

    List<MessageInfo> getMessagesForIssue(IssueinfoDTO issueinfoDTO) throws SQLException;

    List<Issue> getAllIssues() throws SQLException;

    List<MessageInfo> getMessagesByIssueId(IssueinfoDTO issueinfoDTO) throws SQLException;

}
