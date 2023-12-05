using Shared.DTOs;

namespace Blazor.Services;

public interface ILoanService
{
    public Task<string> LoanCalculation(LoanRequestDto dto);
    public Task RequestLoan(LoanRequestDto dto);
}