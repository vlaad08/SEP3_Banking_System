package Database.DAOs;

import Database.AccountsInfo;
import Database.DAOs.Interfaces.LoginDaoInterface;
import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;
import Database.User;

import java.sql.SQLException;
import java.util.List;

public class LoginDao implements LoginDaoInterface {
    SQLConnectionInterface connection;
    {
        try {
            connection = SQLConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<User> getUsers() throws SQLException {
        return connection.getUsers();
    }

    @Override
    public List<AccountsInfo> getAccountsInfo() throws SQLException{
        return connection.getAccountsInfo();
    }

}
