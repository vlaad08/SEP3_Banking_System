namespace Domain.DTOs;

public class LoanCalculationDTO
{
    public string Account { get; set; }
    public double Principal { get; set; }
    public int Tenure { get; set; }
}