namespace Domain.DTOs;

public class LoanRequestDTO
{
    public string AccountNumber { get; set; }
    public double RemainingAmount { get; set; }
    public double Amount { get; set; }
    public int Duration { get; set; }
    public double InterestRate { get; set; }
    public double MonthlyPayment { get; set; }
    public DateTime? EndDate { get; set; }

}