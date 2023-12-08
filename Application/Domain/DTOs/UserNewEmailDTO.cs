namespace Domain.DTOs;

public class UserNewEmailDTO
{
    public int UserID { get; init; }
    public string NewEmail { get; init; }
    public string OldEmail { get; init; }
}