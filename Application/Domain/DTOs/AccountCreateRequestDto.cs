namespace Domain.DTOs;

public class AccountCreateRequestDto
{
    public int User_id { get; init; }
    public string UserAccountNumber { get; init; }
    public double InterestRate { get; init; }
}