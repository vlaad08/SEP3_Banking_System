package Database.DTOs;

public class UserAccountDTO
{
  private int user_id;
  private String userAccountNumber;
  private String accountType;
  private double interestRate;

  public UserAccountDTO(int user_id, String userAccountNumber,
      String accountType, double interestRate)
  {
    this.user_id = user_id;
    this.userAccountNumber = userAccountNumber;
    this.accountType = accountType;
    this.interestRate = interestRate;
  }

  public int getUser_id()
  {
    return user_id;
  }

  public String getUserAccountNumber()
  {
    return userAccountNumber;
  }

  public String getAccountType()
  {
    return accountType;
  }

  public double getInterestRate()
  {
    return interestRate;
  }
}
