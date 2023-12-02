using Application.DaoInterfaces;
using Application.Logic;
using Domain.DTOs;
using Grpc.DAOs;
using Microsoft.VisualBasic.CompilerServices;
using Moq;

namespace Tests.LoanTests;

public class LoanLogicTest
{
    [Fact]
    public async Task CalculateLoan_Gives_Back_An_InterestRate()
    {
        var dto = new LoanCalculationDTO
        {
            Account = "1111222233334444",
            Principal = 4000,
            Tenure = 12
        };
        var loanDao = new Mock<ILoanDAO>();
        var loanLogic = new LoanLogic(loanDao.Object);

        var result = loanLogic.CalculateLoan(dto);
        Assert.IsType<Task<double>>(result);
    }

    [Fact]
    public async Task RequestLoan_Calls_For_Validation_And_Throws_Max_Error()
    {
        var dto = new LoanCalculationDTO
        {
            Account = "1111222233334444",
            Principal = 4000000000,
            Tenure = 12
        };
        var loanDao = new Mock<ILoanDAO>();
        var loanLogic = new LoanLogic(loanDao.Object);
        await Assert.ThrowsAsync<Exception>(() => loanLogic.RequestLoan(dto));
    }
    [Fact]
    public async Task RequestLoan_Calls_For_Validation_And_Throws_Min_Error()
    {
        var dto = new LoanCalculationDTO
        {
            Account = "1111222233334444",
            Principal = 100,
            Tenure = 12
        };
        var loanDao = new Mock<ILoanDAO>();
        var loanLogic = new LoanLogic(loanDao.Object);
        await Assert.ThrowsAsync<Exception>(() => loanLogic.RequestLoan(dto));
    }

    [Fact]
    public async Task RequestLoan_Calls_For_Dao()
    {
        var dto = new LoanCalculationDTO
        {
            Account = "1111222233334444",
            Principal = 10000,
            Tenure = 12
        };
        var loanDao = new Mock<ILoanDAO>();
        var loanLogic = new LoanLogic(loanDao.Object);
        await loanLogic.RequestLoan(dto);
        loanDao.Verify(d => d.RequestLoan(It.IsAny<LoanRequestDTO>()), Times.Once);
    }


    
}