namespace Domain.DTOs;

public class UserNewDetailsRequestDTO
{
    public string NewEmail { get; init; }
    public string OldEmail { get; init; }
    public string Password { get; init; }
    public string Plan { get; init; }
}