package Database.RestController;

import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class AccountController {
    SQLConnectionInterface connection;

    {
        try {
            connection = SQLConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Endpoint to return the available balance of the given account_id, if the account is registered with the given account_id **/
    @GetMapping("/account/{account_id}")
    public synchronized double getBalance(@PathVariable String account_id) throws SQLException
    {
        return connection.getBalanceById(account_id);
    }
}
