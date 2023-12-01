namespace Blazor.Services;

public interface ILoanService
{
    public Task<string> LoanCalculation(double Principle, int Tenure);
}