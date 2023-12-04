package Database.DAOs.Interfaces;

import Database.DTOs.AccountNewBaseRateDTO;
import Database.DTOs.UserNewDetailsRequestDTO;

import java.sql.SQLException;

public interface CredentialChangerDaoInterface
{
  void UpdateNewBaseRate(AccountNewBaseRateDTO accountNewBaseRateDTO)
      throws SQLException;
  void UpdateUserInformation(UserNewDetailsRequestDTO userNewDetailsRequestDTO)
      throws SQLException;
}
