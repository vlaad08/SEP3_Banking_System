using Application.DaoInterfaces;
using Application.Logic;
using DataAccess.DAOs;
using Domain.DTOs;
using Domain.Models;
using Grpc;
using Moq;

namespace Tests.Transfer.Tests;

public class TransferLogicTests
{
    [Fact]
    public async Task TransferMoney_ValidTransfer_ShouldCallTransferDao()
    {
        // Arrange
        var transferRequestDto = new TransferRequestDTO
        {
            SenderAccountNumber = "1111111111111111",
            RecipientAccountNumber = "2222222222222222",
            Amount = 200.0
        };
        var transferDaoMock = new Mock<ITransferDAO>();
        transferDaoMock.Setup(d => d.GetAccountNumberByAccountNumber(transferRequestDto))
            .ReturnsAsync(transferRequestDto.RecipientAccountNumber);
        transferDaoMock.Setup(d => d.GetBalanceByAccountNumber(transferRequestDto))
            .ReturnsAsync(transferRequestDto.Amount);
        transferDaoMock.Setup(d => d.GetTransferAmountsByDayForUser(transferRequestDto))
            .ReturnsAsync(0);
        transferDaoMock.Setup(d => d.GetBalanceByAccountNumber(transferRequestDto))
            .ReturnsAsync(transferRequestDto.Amount+1);

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
        transferDaoMock.Setup(d => d.GetAccountNumberByAccountNumber(transferRequestDto))
            .ReturnsAsync(transferRequestDto.RecipientAccountNumber);
        transferDaoMock.Setup(d => d.GetBalanceByAccountNumber(transferRequestDto))
            .ReturnsAsync(transferRequestDto.Amount - 1);
        transferDaoMock.Setup(d => d.GetTransferAmountsByDayForUser(transferRequestDto))
            .ReturnsAsync(0);
        

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
        transferDaoMock.Setup(d => d.GetAccountNumberByAccountNumber(transferRequestDto))
            .ReturnsAsync("3333333333333333");
        transferDaoMock.Setup(d => d.GetBalanceByAccountNumber(transferRequestDto))
            .ReturnsAsync(transferRequestDto.Amount + 1);
        transferDaoMock.Setup(d => d.GetTransferAmountsByDayForUser(transferRequestDto))
            .ReturnsAsync(0);

        var transferLogic = new TransferLogic(transferDaoMock.Object);

        // Assert
        await Assert.ThrowsAsync<Exception>(() => transferLogic.TransferMoney(transferRequestDto));
    }
    [Fact]
    public async Task ValidateTransfer_DailyLimitReached_ShouldThrowException()
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
        transferDaoMock.Setup(d => d.GetAccountNumberByAccountNumber(transferRequestDto))
            .ReturnsAsync("2222222222222222");
        transferDaoMock.Setup(d => d.GetBalanceByAccountNumber(transferRequestDto))
            .ReturnsAsync(transferRequestDto.Amount + 1);
        transferDaoMock.Setup(d => d.GetTransferAmountsByDayForUser(transferRequestDto))
            .ReturnsAsync(200000);

        var transferLogic = new TransferLogic(transferDaoMock.Object);

        // Assert
        await Assert.ThrowsAsync<Exception>(() => transferLogic.TransferMoney(transferRequestDto));
    }

    [Fact]
    public async Task GetTransactions_Returns_A_List()
    {
        var getTransactionsDTO = new GetTransactionsDTO
        {
            Email = "test@gmail.com"
        };
        var transferDaoMock = new Mock<ITransferDAO>();
        transferDaoMock.Setup(d => d.GetTransactions(It.IsAny<GetTransactionsDTO>()))
            .ReturnsAsync(new List<Transaction>());
        var transferLogic = new TransferLogic(transferDaoMock.Object);

        IEnumerable<Transaction> transactions = await transferLogic.GetTransactions(getTransactionsDTO);

        Assert.IsAssignableFrom<IEnumerable<Transaction>>(transactions);
    }
    [Fact]
    public async Task GetTransactions_Calls_Dao()
    {
        var getTransactionsDTO = new GetTransactionsDTO
        {
            Email = "test@gmail.com"
        };
        var transferDaoMock = new Mock<ITransferDAO>();
        transferDaoMock.Setup(d => d.GetTransactions(It.IsAny<GetTransactionsDTO>()))
            .ReturnsAsync(new List<Transaction>());
        var transferLogic = new TransferLogic(transferDaoMock.Object);

        await transferLogic.GetTransactions(getTransactionsDTO);

        transferDaoMock.Verify(d=>d.GetTransactions(getTransactionsDTO));
    }

    [Fact]
    public async Task GetTransactionsForEmployee_Calls_Dao()
    {
        var transferDaoMock = new Mock<ITransferDAO>();
        transferDaoMock.Setup(d => d.GetTransactions())
            .ReturnsAsync(new List<Transaction>());
        var transferLogic = new TransferLogic(transferDaoMock.Object);
        await transferLogic.GetTransactions();
        transferDaoMock.Verify(d=>d.GetTransactions());
    }
    [Fact]
    public async Task GetTransactionsForEmployee_Returns_A_List()
    {
        var transferDaoMock = new Mock<ITransferDAO>();
        transferDaoMock.Setup(d => d.GetTransactions())
            .ReturnsAsync(new List<Transaction>());
        var transferLogic = new TransferLogic(transferDaoMock.Object);
        IEnumerable<Transaction> transactions = await transferLogic.GetTransactions();
        Assert.IsAssignableFrom<IEnumerable<Transaction>>(transactions);
    }

    [Fact]
    public async Task FlagUser_Calls_For_Dao()
    {
        FlagUserDTO dto = new FlagUserDTO
        {
            SenderId = 1
        };
        var transferDaoMock = new Mock<ITransferDAO>();
        var transferLogic = new TransferLogic(transferDaoMock.Object);
        await transferLogic.FlagUser(dto);
        transferDaoMock.Verify(d=>d.FlagUser(dto));
    }

    
    
}