package Database.DAOs.Interfaces;

import Database.AccountsInfo;
import Database.DTOs.LoanRequestDTO;
import Database.DTOs.UserInfoEmailDTO;
import Database.User;

import java.sql.SQLException;
import java.util.List;

public interface LoginDaoInterface {
    List<User> getUsers() throws SQLException;
    List<AccountsInfo> getAccountsInfo() throws SQLException;
    List<AccountsInfo> getUserAccountInfos(UserInfoEmailDTO userInfoDTO) throws SQLException;

}
