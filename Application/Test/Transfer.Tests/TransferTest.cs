using Application.DaoInterfaces;
using Application.Logic;
using DataAccess.DAOs;
using Domain.DTOs;
using Grpc;
using Moq;

namespace PrimeService.Tests;

public class TransferTest
{
    [Fact]
    public async Task TransferMoney_ValidTransfer_ShouldCallTransferDao()
    {
        // Arrange
        var transferRequestDto = new TransferRequestDTO
        {
            SenderAccountNumber = "1111111111111111",
            RecipientAccountNumber = "2222222222222222",
            Amount = 69420.0
        };

        var transferDaoMock = new Mock<ITransferDAO>();
        transferDaoMock.Setup(d => d.GetAccountNumberByAccountNumber(It.IsAny<string>()))
            .ReturnsAsync(transferRequestDto.RecipientAccountNumber);
        transferDaoMock.Setup(d => d.GetBalanceByAccountNumber(It.IsAny<string>()))
            .ReturnsAsync(transferRequestDto.Amount + 1);

        var transferLogic = new TransferLogic(transferDaoMock.Object);

        // Act
        await transferLogic.TransferMoney(transferRequestDto);

        // Assert
        transferDaoMock.Verify(d => d.TransferMoney(transferRequestDto), Times.Once);
    }
    
    [Fact]
    public async Task ValidateTransfer_InsufficientBalance_ShouldThrowException()
    {
        // Arrange
        var transferRequestDto = new TransferRequestDTO
        {
            SenderAccountNumber = "1111111111111111",
            RecipientAccountNumber = "2222222222222222",
            Amount = 69420.0
        };

        var transferDaoMock = new Mock<ITransferDAO>();
        transferDaoMock.Setup(d => d.GetAccountNumberByAccountNumber(It.IsAny<string>()))
            .ReturnsAsync(transferRequestDto.RecipientAccountNumber);
        transferDaoMock.Setup(d => d.GetBalanceByAccountNumber(It.IsAny<string>()))
            .ReturnsAsync(transferRequestDto.Amount - 1); 

        var transferLogic = new TransferLogic(transferDaoMock.Object);

        // Assert
        await Assert.ThrowsAsync<Exception>(() => transferLogic.TransferMoney(transferRequestDto));
    }
    
    [Fact]
    public async Task ValidateTransfer_AccountNumberDoesntExist_ShouldThrowException()
    {
        // Arrange
        var transferRequestDto = new TransferRequestDTO
        {
            SenderAccountNumber = "1111111111111111",
            RecipientAccountNumber = "2222222222222222",
            Amount = 69420.0
        };

        //Act
        var transferDaoMock = new Mock<ITransferDAO>();
        transferDaoMock.Setup(d => d.GetAccountNumberByAccountNumber(It.IsAny<string>()))
            .ReturnsAsync("3333333333333333");
        transferDaoMock.Setup(d => d.GetBalanceByAccountNumber(It.IsAny<string>()))
            .ReturnsAsync(transferRequestDto.Amount + 1); 

        var transferLogic = new TransferLogic(transferDaoMock.Object);

        // Assert
        await Assert.ThrowsAsync<Exception>(() => transferLogic.TransferMoney(transferRequestDto));
    }
    
    
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
        var transferDAO = new TransferDAO(grpcClientMock.Object);

        // Act
        await transferDAO.TransferMoney(transferRequestDto);

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

        var transferDAO = new TransferDAO(grpcClientMock.Object);

        // Act
        var result = await transferDAO.GetBalanceByAccountNumber(accountNumber);

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

        var transferDAO = new TransferDAO(grpcClientMock.Object);

        // Act
        var result = await transferDAO.GetAccountNumberByAccountNumber(accountNumber);

        // Assert
        Assert.IsType<string>(result);
        Assert.Equal(expectedAccount, result);
        grpcClientMock.Verify(c => c.GetAccountNumberByAccountNumber(accountNumber), Times.Once);
    }


}