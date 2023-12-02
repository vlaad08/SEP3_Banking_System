package Database.DAOs.Interfaces;

import Database.DTOs.RegisterUserDTO;

import java.sql.SQLException;

public interface RegisterDaoInterface
{
  void registerUser(RegisterUserDTO registerUserDTO) throws SQLException;
}
