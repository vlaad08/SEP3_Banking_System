package Database.DataAccess;

import java.sql.*;

public class SQLConnection implements SQLConnectionInterface{
    private static SQLConnection instance;
    protected SQLConnection() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }
    public static SQLConnection getInstance() throws SQLException
    {
        if (instance ==null) {
            instance = new SQLConnection();
        }
        return instance;
    }
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection("?????????????","username","password");
    }
    /** Database manipulator method, to make the transfer in the database with the given details**/
    @Override
    public void transfer(String id_1, String id_2, double amount) {
        try (Connection connection = (Connection) getInstance())
        {
            PreparedStatement statement = connection.prepareStatement("BEGIN; UPDATE account SET balance=balance-? WHERE account_id=? UPDATE account SET balance=balance+? WHERE account_id=? COMMIT;");
            statement.setString(1,id_1);
            statement.setDouble(2,amount);
            statement.setDouble(3,amount);
            statement.setString(4,id_2);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /** Database query method, to make the query for the available balance for the given account_id**/
    @Override
    public double getBalanceById(String account_id) throws SQLException {
        double balance=0;
        try (Connection connection= (Connection) getInstance())
        {
            PreparedStatement statement=connection.prepareStatement("SELECT balance FROM account WHERE account_id=?;");
            statement.setString(1,account_id);
            ResultSet result = statement.executeQuery();
            balance=result.getDouble("balance");
        }
        return balance;
    }
}
