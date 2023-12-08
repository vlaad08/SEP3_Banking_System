package Database.DTOs;

public class UserNewEmailDTO
{
  private int UserID;
  private String Email;

  public UserNewEmailDTO(int userID, String email)
  {
    UserID = userID;
    Email = email;
  }

  public int getUserID()
  {
    return UserID;
  }

  public String getEmail()
  {
    return Email;
  }
}
