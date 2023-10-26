package Database.DataAccess;

import java.sql.SQLException;

public interface SQLConnectionInterface {
    public void transfer(String id_1, String id_2, double amount, String message);
    public double getBalanceById(String account_id) throws SQLException;
}
