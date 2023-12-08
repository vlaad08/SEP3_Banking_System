namespace Domain.DTOs;

public class UserNewPasswordDTO
{
    public int UserID { get; init; }
    public string newPassword { get; init; }

    public UserNewPasswordDTO(int userId, string NewPassword)
    {
        UserID = userId;
        newPassword = NewPassword;
    }
}