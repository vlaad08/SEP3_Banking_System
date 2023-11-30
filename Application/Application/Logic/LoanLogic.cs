using Application.LogicInterfaces;
using Domain.DTOs;

namespace Application.Logic;

public class LoanLogic : ILoanLogic
{
    public async Task<double> CalculateLoan(LoanCalculationDTO dto)
    {
        double P = dto.Principal;
        int B = dto.Tenure;
        double R = 10.00;
        double estimated = (P * R * Math.Pow((1 + B), B)) / (Math.Pow((1 + R), B-1));
        double amountFactor = 1 + P / 10000;
        double durationFactor = 1 + B / 12.0; 
        double interestRate = R * amountFactor * durationFactor;
        return interestRate;
    }

    public async Task RequestLoan(LoanRequestDTO dto)
    {
        throw new NotImplementedException();
    }
}