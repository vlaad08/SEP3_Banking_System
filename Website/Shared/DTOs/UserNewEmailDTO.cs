namespace Domain.DTOs;

public class UserNewEmailDTO
{
    public int UserID { get; init; }
    public string NewEmail { get; init; }
    public string OldEmail { get; init; }

    public UserNewEmailDTO(int userID, string newEmail, string oldEmail)
    {
        UserID = userID;
        NewEmail = newEmail;
        OldEmail = oldEmail;
    }
}