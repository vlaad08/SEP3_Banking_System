package Database.DTOs;

public class UserNewDetailsRequestDTO
{
  private String NewEmail;
  private String OldEmail;
  private String Password;
  private String Plan;

  public UserNewDetailsRequestDTO(String newEmail, String oldEmail,
      String password, String plan)
  {
    NewEmail = newEmail;
    OldEmail = oldEmail;
    Password = password;
    Plan = plan;
  }

  public String getNewEmail()
  {
    return NewEmail;
  }

  public String getOldEmail()
  {
    return OldEmail;
  }

  public String getPassword()
  {
    return Password;
  }

  public String getPlan()
  {
    return Plan;
  }
}
