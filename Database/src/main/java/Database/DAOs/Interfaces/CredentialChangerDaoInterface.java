package Database.DAOs.Interfaces;

import Database.DTOs.*;

import java.sql.SQLException;

public interface CredentialChangerDaoInterface
{
  void UpdateBaseRate(AccountNewBaseRateDTO accountNewBaseRateDTO)
      throws SQLException;
  void UpdateUserInformation(UserNewDetailsRequestDTO userNewDetailsRequestDTO)
      throws SQLException;


  void UpdateEmail(UserNewEmailDTO userNewEmailDTO) throws SQLException;
  void UpdatePassword(UserNewPasswordDTO userNewPasswordDTO) throws SQLException;
  void UpdatePlan(UserNewPlanDTO userNewPlanDTO) throws SQLException;
}
