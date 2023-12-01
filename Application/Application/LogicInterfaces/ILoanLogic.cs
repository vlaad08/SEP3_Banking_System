using Domain.DTOs;

namespace Application.LogicInterfaces;

public interface ILoanLogic
{
    Task<double> CalculateLoan(LoanCalculationDTO dto);
    Task RequestLoan(LoanCalculationDTO dto);
}