package Database.DAOs;

import Database.DAOs.Interfaces.CredentialChangerDaoInterface;
import Database.DTOs.*;
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

  @Override public void UpdateBaseRate(
      AccountNewBaseRateDTO accountNewBaseRateDTO) throws SQLException
  {
      connection.updateNewBaseRate(accountNewBaseRateDTO);
  }

  @Override public void UpdateUserInformation(
      UserNewDetailsRequestDTO userNewDetailsRequestDTO) throws SQLException
  {
      connection.updateUserInformation(userNewDetailsRequestDTO);
  }

  @Override public void UpdateEmail(UserNewEmailDTO userNewEmailDTO)
      throws SQLException
  {
    connection.updateEmail(userNewEmailDTO);
  }

  @Override public void UpdatePassword(UserNewPasswordDTO userNewPasswordDTO)
      throws SQLException
  {
    connection.updatePassword(userNewPasswordDTO);
  }

  @Override public void UpdatePlan(UserNewPlanDTO userNewPlanDTO)
      throws SQLException
  {
    connection.updatePlan(userNewPlanDTO);
  }
}
