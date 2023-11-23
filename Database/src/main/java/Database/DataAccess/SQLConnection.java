package Database.DataAccess;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=banking_system","postgres","password");
    }
    /** Database manipulator method, to make the transfer in the database with the given details**/
    @Override
    public void transfer(String id_1, String id_2, double amount, String message) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement updateStatement1 = connection.prepareStatement(
                    "UPDATE account SET balance = balance + ? WHERE account_id = ?")) {

                updateStatement1.setDouble(1, amount);
                updateStatement1.setString(2, id_2);
                updateStatement1.executeUpdate();

                try (PreparedStatement updateStatement2 = connection.prepareStatement(
                        "UPDATE account SET balance = balance - ? WHERE account_id = ?")) {

                    updateStatement2.setDouble(1, amount);
                    updateStatement2.setString(2, id_1);
                    updateStatement2.executeUpdate();

                    try (PreparedStatement insertStatement = connection.prepareStatement(
                            "INSERT INTO transactions(dateTime, amount, message, senderAccount_id, recipientAccount_id) " +
                                    "VALUES (?, ?, ?, ?, ?)")) {

                        insertStatement.setTimestamp(1, now);
                        insertStatement.setDouble(2, amount);
                        insertStatement.setString(3, message);
                        insertStatement.setString(4, id_1);
                        insertStatement.setString(5, id_2);
                        insertStatement.executeUpdate();

                        connection.commit();
                    } catch (SQLException e) {
                        connection.rollback();
                        throw new RuntimeException("Error executing insert statement", e);
                    }
                } catch (SQLException e) {
                    connection.rollback();
                    throw new RuntimeException("Error executing second update statement", e);
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Error executing first update statement", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error opening/closing connection", e);
        }
    }

    /** Database query method, to make the query for the available balance for the given account_id**/
    @Override
    public double checkBalance(String account_id) throws SQLException {
        double balance=0;
        try (Connection connection= getConnection())
        {
            PreparedStatement statement=connection.prepareStatement("SELECT balance FROM account WHERE account_id=?;");
            statement.setString(1,account_id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                balance = result.getDouble("balance");
            }
        }
        return balance;
    }

    @Override
    public String checkAccountId(String account_id) throws SQLException {
        String recipientAccount_id = "-";
        try(Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("SELECT account_id FROM account WHERE account_id = ?;");
            statement.setString(1, account_id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                recipientAccount_id = result.getString("account_id");
            }
        }
        return recipientAccount_id;
    }

    @Override
    public double dailyCheck(String account_id) throws SQLException {
        double amount = 0;
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("SELECT SUM(amount)\n" +
                    "FROM transactions\n" +
                    "WHERE senderAccount_id = ?\n" +
                    "  AND DATE_TRUNC('day', dateTime) = CURRENT_DATE;");
            statement.setString(1,account_id);
            ResultSet result = statement.executeQuery();
            if (result.next())
            {
                amount = result.getDouble("sum");
            }
        }
        return amount;
    }
}
