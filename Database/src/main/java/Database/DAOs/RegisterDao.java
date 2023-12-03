package Database.DAOs;

import Database.DAOs.Interfaces.RegisterDaoInterface;
import Database.DTOs.RegisterRequestDTO;
import Database.DTOs.UserAccountDTO;
import Database.DTOs.UserAccountRequestDTO;
import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;

import java.sql.SQLException;

public class RegisterDao implements RegisterDaoInterface
{
  SQLConnectionInterface connection;
  {
    try {
      connection = SQLConnection.getInstance();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  @Override public void registerUser(RegisterRequestDTO registerUserDTO)
      throws SQLException
  {
    connection.registerUser(registerUserDTO);
  }

  @Override public int getUserID(UserAccountRequestDTO userAccountRequestDTO)
      throws SQLException
  {
    return connection.getUserID(userAccountRequestDTO);
  }

  @Override public void generateAccountNumber(UserAccountDTO userAccountDTO) throws SQLException
  {
    connection.generateAccountNumber(userAccountDTO);
  }

  @Override public String getUserEmail(
      UserAccountRequestDTO userAccountRequestDTO) throws SQLException
  {
    return connection.getUserEmail(userAccountRequestDTO);
  }
}
