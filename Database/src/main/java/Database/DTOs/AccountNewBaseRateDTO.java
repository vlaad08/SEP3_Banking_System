package Database.DTOs;

public class AccountNewBaseRateDTO
{
  private int user_id;
  private double baseRate;

  public AccountNewBaseRateDTO(int user_id, double baseRate)
  {
    this.user_id = user_id;
    this.baseRate = baseRate;
  }

  public int getUser_id()
  {
    return user_id;
  }

  public double getBaseRate()
  {
    return baseRate;
  }
}
