namespace Domain.DTOs;

public class UserNewPlanDTO
{
    public int UserID { get; init; }
    public string Plan { get; init; }

    public UserNewPlanDTO(int userId, string plan)
    {
        UserID = userId;
        Plan = plan;
    }
}