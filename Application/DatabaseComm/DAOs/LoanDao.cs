using Application.DaoInterfaces;
using Domain.DTOs;

namespace Grpc.DAOs;

public class LoanDao : ILoanDAO
{
    public Task RequestLoan(LoanRequestDTO dto)
    {
        throw new NotImplementedException();
    }
}