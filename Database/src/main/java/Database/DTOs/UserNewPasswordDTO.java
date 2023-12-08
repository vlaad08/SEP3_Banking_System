package Database.DTOs;

public class UserNewPasswordDTO
{
  private int UserID;
  private String Password;

  public UserNewPasswordDTO(int userID, String password)
  {
    UserID = userID;
    Password = password;
  }

  public int getUserID()
  {
    return UserID;
  }

  public String getPassword()
  {
    return Password;
  }
}
