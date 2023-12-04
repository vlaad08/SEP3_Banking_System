package Database.DTOs;

public class UserAccountRequestDTO
{
  private String email;

  public UserAccountRequestDTO(String email)
  {
    this.email = email;
  }

  public String getEmail()
  {
    return email;
  }
}
