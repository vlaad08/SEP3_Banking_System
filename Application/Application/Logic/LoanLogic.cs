using Application.LogicInterfaces;
using Domain.DTOs;

namespace Application.Logic;

public class LoanLogic : ILoanLogic
{
    public async Task<double> CalculateLoan(LoanCalculationDTO dto)
    {
        double P = dto.Principal;
        int N = dto.Tenure;
        double R = 10.00;
        double interestRate = (P * R * Math.Pow((1 + N), N)) / (Math.Pow((1 + R), N) - 1);
        return interestRate;
    }

    public async Task RequestLoan(LoanRequestDTO dto)
    {
        throw new NotImplementedException();
    }
}