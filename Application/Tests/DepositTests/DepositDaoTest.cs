using DataAccess.DAOs;
using Domain.DTOs;
using Grpc;
using Moq;

namespace Tests.DepositTests;

public class DepositDaoTest
{
    [Fact]
    public async void MakeDeposit_Calls_For_GrpcClient()
    {
        UpdatedDepositDTO dto = new UpdatedDepositDTO
        {
            AccountId = "aaaabbbbbcccccdddd",
            Amount = 10, 
            UpdatedBalance = 1110
        };
        var grpc = new Mock<IGrpcClient>();
        var dao = new DepositDao(grpc.Object);
        await dao.DepositMoney(dto);
        
        grpc.Verify(g => g.MakeDeposit(dto));
    }

    [Fact]
    public async void GetBalanceByAccountNumber_Calls_For_GrpcClient()
    {
        var grpc = new Mock<IGrpcClient>();
        var dao = new DepositDao(grpc.Object);
        DepositRequestDTO dto = new DepositRequestDTO
        {
            Amount = 2222,
            ToppedUpAccountNumber = "-"
        };
        await dao.GetBalanceByAccountNumber(dto);
        grpc.Verify(g=>g.GetBalanceByAccountNumber(It.IsAny<GetBalanceDTO>()));
    }
}