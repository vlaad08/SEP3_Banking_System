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
        DepositRequestDTO dto = new DepositRequestDTO
        {
            Amount = 10,
            ToppedUpAccountNumber = "1111111111111111"
        };
        var grpc = new Mock<IGrpcClient>();
        var dao = new DepositDao(grpc.Object);
        
        await dao.DepositMoney(dto);
        
        grpc.Verify(g => g.MakeDeposit(dto));
    }
}