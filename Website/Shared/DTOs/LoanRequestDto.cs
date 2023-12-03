namespace Shared.DTOs;

public class LoanRequestDto
{
    public string AccountNumber { get; set; }
    public double Principal { get; set; }
    public int Tenure { get; set; }

    public LoanRequestDto(string accountNumber, double principal, int tenure)
    {
        AccountNumber = accountNumber;
        Principal = principal;
        Tenure = tenure;
    }
}