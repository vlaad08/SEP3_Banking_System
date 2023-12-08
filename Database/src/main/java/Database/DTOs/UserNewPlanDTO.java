package Database.DTOs;

public class UserNewPlanDTO
{
  private int UserID;
  private String Plan;

  public UserNewPlanDTO(int userID, String plan)
  {
    UserID = userID;
    Plan = plan;
  }

  public int getUserID()
  {
    return UserID;
  }

  public String getPlan()
  {
    return Plan;
  }
}
