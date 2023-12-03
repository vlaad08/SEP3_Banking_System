package Database.DTOs;

public class RegisterRequestDTO
{
  private String email;
  private String firstname;
  private String middlename;
  private String lastname;
  private String password;

  public RegisterRequestDTO(String email, String firstname, String middlename,
      String lastname, String password)
  {
    this.email = email;
    this.firstname = firstname;
    this.middlename = middlename;
    this.lastname = lastname;
    this.password = password;
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

}
