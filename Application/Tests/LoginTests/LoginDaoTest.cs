using Grpc;
using Grpc.DAOs;
using Moq;
using Shared.DTOs;

namespace Tests;

public class LoginDaoTest
{
    [Fact]
    public async void GetAllUserDataForValidation_With_Request_Calls_GrpcClient()
    {
        UserLoginRequestDto dto = new UserLoginRequestDto
        {
            Email = "user1@gmail.com",
            Password = "password1"
        };
        var grpc = new Mock<IGrpcClient>();
        var dao = new UserLoginDao(grpc.Object);

        await dao.GetAllUserDataForValidation(dto);
        
        grpc.Verify(g => g.GetAllUserInfo());
    }
    [Fact]
    public async void GetAllUserDataForValidation_Calls_GrpcClient()
    {
        var grpc = new Mock<IGrpcClient>();
        var dao = new UserLoginDao(grpc.Object);

        await dao.GetAllUserDataForValidation();
        
        grpc.Verify(g => g.GetAllUserInfo());
    }
    [Fact]
    public async void GetAccounts_Calls_GrpcClient()
    {
        var grpc = new Mock<IGrpcClient>();
        var dao = new UserLoginDao(grpc.Object);

        await dao.GetAccounts();
        
        grpc.Verify(g => g.GetAllAccountsInfo());
    }
    [Fact]
    public async void GetUserAccounts_Calls_GrpcClient()
    {
        UserLoginRequestDto dto = new UserLoginRequestDto
        {
            Email = "user1@gmail.com",
            Password = "password1"
        };
        var grpc = new Mock<IGrpcClient>();
        var dao = new UserLoginDao(grpc.Object);

        await dao.GetUserAccounts(dto);
        
        grpc.Verify(g => g.GetUserAccounts(dto));
    }
}