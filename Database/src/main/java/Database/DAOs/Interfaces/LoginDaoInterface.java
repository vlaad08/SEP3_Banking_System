package Database.DAOs.Interfaces;

import Database.AccountsInfo;
import Database.User;

import java.sql.SQLException;
import java.util.List;

public interface LoginDaoInterface {
    List<User> getUsers() throws SQLException;
    List<AccountsInfo> getAccountsInfo() throws SQLException;
}
