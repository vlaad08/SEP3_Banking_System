package Database.DTOs;

public class UserAccountDTO
{
  private int user_id;
  private String userAccountNumber;
  private double interestRate;

  public UserAccountDTO(
      String user_id, String userAccountNumber, String interestRate)
  {
    this.user_id = Integer.parseInt(user_id);
    this.userAccountNumber = userAccountNumber;
    this.interestRate = Double.parseDouble(interestRate);
  }

  public int getUser_id()
  {
    return user_id;
  }

  public String getUserAccountNumber()
  {
    return userAccountNumber;
  }

  public double getInterestRate()
  {
    return interestRate;
  }
}
