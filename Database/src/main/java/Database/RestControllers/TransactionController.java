package Database.RestControllers;

import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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

    @RequestMapping()
    public String ok() {
        return "This is the root!";
    }

    /** Endpoint to make the transaction in the database **/
    @PostMapping("transactions/")
    public synchronized void transfer(@RequestBody String transferDto) {
         System.out.println(transferDto);
         HashMap<String,Object> map = createHash(transferDto);
         List<String> keys = new ArrayList<>(map.keySet());
         List<Object> values = new ArrayList<>(map.values());
         String senderAccount_id= (String) values.get(0);
         String recipientAccount_id= (String) values.get(1);
         double amount= Double.parseDouble((String) values.get(2));
         String message = (String) values.get(3);
         System.out.println(senderAccount_id+" "+recipientAccount_id );
         connection.transfer(senderAccount_id,recipientAccount_id,amount,message);

        /*
         * String id_1 = transferDto.getId_1();
         * String id_2 = transferDto.getId_2();
         * double amount = transferDto.getAmount();
         * connection.transfer(id_1,id_2,amount);
         */
    }

    private synchronized HashMap<String, Object> createHash(String json) {
        HashMap<String, Object> map = new HashMap<>();
        String newJson = json.substring(1, json.length() - 1);
        String[] result = newJson.split("[:,]");
        for (int i = 0; i < result.length; i += 2) {
            String key = result[i].trim();
            String value = result[i + 1].trim();
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }
            map.put(key, value);
        }
        return map;
    }

}
