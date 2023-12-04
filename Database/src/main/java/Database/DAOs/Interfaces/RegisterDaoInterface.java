package Database.DAOs.Interfaces;

import Database.DTOs.RegisterRequestDTO;
import Database.DTOs.UserAccountDTO;
import Database.DTOs.UserAccountRequestDTO;

import java.sql.SQLException;

public interface RegisterDaoInterface
{
  void registerUser(RegisterRequestDTO registerUserDTO) throws SQLException;

  int getUserID(UserAccountRequestDTO userAccountRequestDTO) throws SQLException;

  void generateAccountNumber(UserAccountDTO userAccountDTO) throws SQLException;

  String getUserEmail(UserAccountRequestDTO userAccountRequestDTO) throws SQLException;
}
