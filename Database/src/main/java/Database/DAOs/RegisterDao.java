package Database.DAOs;

import Database.DAOs.Interfaces.RegisterDaoInterface;
import Database.DTOs.RegisterUserDTO;
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
  @Override public void registerUser(RegisterUserDTO registerUserDTO)
      throws SQLException
  {
    connection.registerUser(registerUserDTO);
  }
}
