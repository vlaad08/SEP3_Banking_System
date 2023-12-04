package Database.DAOs;

import Database.DAOs.Interfaces.CredentialChangerDaoInterface;
import Database.DTOs.AccountNewBaseRateDTO;
import Database.DTOs.UserNewDetailsRequestDTO;
import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;

import java.sql.SQLException;

public class CredentialChangerDao implements CredentialChangerDaoInterface
{
  SQLConnectionInterface connection;
  {
    try {
      connection = SQLConnection.getInstance();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override public void UpdateNewBaseRate(
      AccountNewBaseRateDTO accountNewBaseRateDTO) throws SQLException
  {
      connection.updateNewBaseRate(accountNewBaseRateDTO);
  }

  @Override public void UpdateUserInformation(
      UserNewDetailsRequestDTO userNewDetailsRequestDTO) throws SQLException
  {
      connection.updateUserInformation(userNewDetailsRequestDTO);
  }
}
