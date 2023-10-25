package Database.RestController;

import Database.DTOs.TransferDto;
import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class TransactionController {
    SQLConnectionInterface connection;

    {
        try {
            connection = SQLConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /** Endpoint to make the transaction in the database**/
    @PostMapping("transactions/")
    public synchronized void transfer(@RequestBody TransferDto transferDto)
    {
        String id_1 = transferDto.getId_1();
        String id_2 = transferDto.getId_2();
        double amount = transferDto.getAmount();
        connection.transfer(id_1,id_2,amount);
    }
}
