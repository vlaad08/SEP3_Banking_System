using DataAccess.DAOs;
using Domain.DTOs;
using Grpc;
using Moq;

namespace Tests.Transfer.Tests;

public class TransferDaoTest
{
    [Fact]
    public async Task TransferMoney_ValidTransfer_ShouldCallGrpcClient()
    {
        // Arrange
        var transferRequestDto = new TransferRequestDTO
        {
            SenderAccountNumber = "1111111111111111",
            RecipientAccountNumber = "2222222222222222",
            Amount = 69420.0
        };

        var grpcClientMock = new Mock<IGrpcClient>();
        var transferDao = new TransferDAO(grpcClientMock.Object);

        // Act
        await transferDao.TransferMoney(transferRequestDto);

        // Assert
        grpcClientMock.Verify(c => c.MakeTransfer(transferRequestDto), Times.Once);
    }
    
    [Fact]
    public async Task GetBalanceByAccountNumber_ValidAccountNumber_ShouldCallGrpcClient()
    {
        // Arrange
        var accountNumber = "1111111111111111"; 
        var expectedBalance = 69420; 

        var grpcClientMock = new Mock<IGrpcClient>();
        grpcClientMock.Setup(c => c.GetBalanceByAccountNumber(accountNumber))
            .ReturnsAsync(expectedBalance);

        var transferDao = new TransferDAO(grpcClientMock.Object);

        // Act
        var result = await transferDao.GetBalanceByAccountNumber(accountNumber);

        // Assert
        Assert.IsType<double>(result);
        Assert.Equal(expectedBalance, result);
        grpcClientMock.Verify(c => c.GetBalanceByAccountNumber(accountNumber), Times.Once);
    }

    [Fact]
    public async Task GetAccountNumberByAccountNumber_ValidAccountNumber_ShouldCallGrpcClientAndReturnString()
    {
        // Arrange
        var accountNumber = "1111111111111111"; 
        var expectedAccount = "1111111111111111"; 

        var grpcClientMock = new Mock<IGrpcClient>();
        grpcClientMock.Setup(c => c.GetAccountNumberByAccountNumber(accountNumber))
            .ReturnsAsync(expectedAccount);

        var transferDao = new TransferDAO(grpcClientMock.Object);

        // Act
        var result = await transferDao.GetAccountNumberByAccountNumber(accountNumber);

        // Assert
        Assert.IsType<string>(result);
        Assert.Equal(expectedAccount, result);
        grpcClientMock.Verify(c => c.GetAccountNumberByAccountNumber(accountNumber), Times.Once);
    }
    [Fact]
    public async Task GetTransferAmountsByDayForUser_ValidAccountNumber_ShouldCallGrpcClientAndReturnString()
    {
        // Arrange
        var accountNumber = "1111111111111111"; 
        var expectedAmount = 2222; 

        var grpcClientMock = new Mock<IGrpcClient>();
        grpcClientMock.Setup(c => c.DailyCheck(accountNumber))
            .ReturnsAsync(expectedAmount);

        var transferDao = new TransferDAO(grpcClientMock.Object);

        // Act
        var result = await transferDao.GetTransferAmountsByDayForUser(accountNumber);

        // Assert
        Assert.IsType<double>(result);
        Assert.Equal(expectedAmount, result);
        grpcClientMock.Verify(c => c.DailyCheck(accountNumber), Times.Once);
    }
}