using Application.Logic;
using Domain.DTOs;
using Grpc;
using Grpc.DAOs;
using Moq;

namespace Tests.LoanTests;

public class LoanDaoTest
{
    [Fact]
    public async Task RequestLoan_Calls_For_grpc()
    {
        var dto = new LoanRequestDTO 
        {
            AccountNumber = "1111222233334444",
            RemainingAmount = 4500,
            Amount = 3000,
            Duration = 16,
            InterestRate = 24,
            MonthlyPayment = 450,
            EndDate = DateTime.Today 
        };
        var grpcClient = new Mock<IGrpcClient>();
        var loanDao = new LoanDao(grpcClient.Object);
        loanDao.RequestLoan(dto);
        grpcClient.Verify(g=>g.RequestLoan(dto));
    }
}