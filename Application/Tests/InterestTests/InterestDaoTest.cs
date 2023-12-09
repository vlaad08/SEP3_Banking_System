using Domain.DTOs;
using Grpc;
using Grpc.DAOs;
using Moq;

namespace Tests.InterestTests;

public class InterestDaoTest
{
    private readonly Mock<IGrpcClient> mockGrpcClient;
    private readonly InterestDao interestDao;

    public InterestDaoTest()
    {
        mockGrpcClient = new Mock<IGrpcClient>();
        interestDao = new InterestDao(mockGrpcClient.Object);
    }
    [Fact]
    public async Task CreditInterest_Calls_For_Grpc()
    {
        await interestDao.CreditInterest(It.IsAny<CreditInterestDTO>());
        mockGrpcClient.Verify(g=>g.CreditInterest(It.IsAny<CreditInterestDTO>()));
    }
    [Fact]
    public async Task CheckInterest_Calls_For_Grpc()
    {
        await interestDao.CheckInterest(It.IsAny<InterestCheckDTO>());
        mockGrpcClient.Verify(g=>g.CheckInterest(It.IsAny<InterestCheckDTO>()));
    }
    [Fact]
    public async Task GetBalanceByAccountNumber_Calls_For_Grpc()
    {
        InterestCheckDTO interestCheckDto = new InterestCheckDTO
        {
            AccountID = "-"
        };
        await interestDao.GetBalanceByAccountNumber(interestCheckDto);
        mockGrpcClient.Verify(g=>g.GetBalanceByAccountNumber(It.IsAny<GetBalanceDTO>()));
    }
    
    [Fact]
    public async Task GetInterestRateByAccountNumber_Calls_For_Grpc()
    {
        InterestCheckDTO interestCheckDto = new InterestCheckDTO
        {
            AccountID = "-"
        };
        await interestDao.GetInterestRateByAccountNumber(interestCheckDto);
        mockGrpcClient.Verify(g=>g.GetInterestRateByAccountNumber(It.IsAny<GetBalanceDTO>()));
    }
}