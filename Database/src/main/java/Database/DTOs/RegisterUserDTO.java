package Database.DTOs;

public class RegisterUserDTO
{
  private String email;
  private String firstname;
  private String middlename;
  private String lastname;
  private String password;
  private String plan;

  public RegisterUserDTO(String email, String firstname, String middlename,
      String lastname, String password, String plan)
  {
    this.email = email;
    this.firstname = firstname;
    this.middlename = middlename;
    this.lastname = lastname;
    this.password = password;
    this.plan = plan;
  }

  public String getEmail()
  {
    return email;
  }

  public String getFirstname()
  {
    return firstname;
  }

  public String getMiddlename()
  {
    return middlename;
  }

  public String getLastname()
  {
    return lastname;
  }

  public String getPassword()
  {
    return password;
  }

  public String getPlan()
  {
    return plan;
  }
}
