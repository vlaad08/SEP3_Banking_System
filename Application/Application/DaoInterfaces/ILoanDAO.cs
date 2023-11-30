using Domain.DTOs;

namespace Application.DaoInterfaces;

public interface ILoanDAO
{
    Task RequestLoan(LoanRequestDTO dto);
}