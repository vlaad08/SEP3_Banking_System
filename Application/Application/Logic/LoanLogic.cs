using Application.DaoInterfaces;
using Application.LogicInterfaces;
using Domain.DTOs;

namespace Application.Logic;

public class LoanLogic : ILoanLogic
{
    private double interestRate;
    private readonly ILoanDAO loanDao;

    public LoanLogic(ILoanDAO loanDao)
    {
        this.loanDao = loanDao;
    }

    public async Task<double> CalculateLoan(LoanCalculationDTO dto)
    {
        double P = dto.Principal;
        int T = dto.Tenure;
        double B = 7;
        double estimated = (P * B * Math.Pow((1 + T), T)) / (Math.Pow((1 + B), T-1));
        double amountFactor = 1 + P / 10000;
        double durationFactor = 1 + T / 12.0; 
        interestRate = B + amountFactor * durationFactor;
        return interestRate;
    }

    public async Task RequestLoan(LoanCalculationDTO dto)
    {
        await ValidateLoan(dto);
        await CalculateLoan(dto);
        DateTime now = DateTime.Now;
        DateTime endDate = now.AddMonths(dto.Tenure);
        LoanRequestDTO loanRequestDto = new LoanRequestDTO
        {
            AccountNumber = dto.Account,
            RemainingAmount = (dto.Principal * (1 + (interestRate / 100))),
            Amount = dto.Principal,
            Duration = dto.Tenure,
            InterestRate = interestRate,
            MonthlyPayment = ((dto.Principal * (1 + (interestRate / 100)))/dto.Tenure),
            EndDate = endDate
        };
        await loanDao.RequestLoan(loanRequestDto);
    }

    private Task ValidateLoan(LoanCalculationDTO loanRequestDto)
    {
        if (loanRequestDto.Principal>1000000)
        {
            throw new Exception("Amount exceeds loan limit!");
        }

        if (loanRequestDto.Principal < 1000)
        {
            throw new Exception("Amount doesn't reach the minimum amount for a loan!");
        }
        return Task.CompletedTask;
    }
}