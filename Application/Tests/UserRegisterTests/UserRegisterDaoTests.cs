using Xunit;
using Moq;
using Grpc.DAOs;
using Domain.DTOs;
using System.Threading.Tasks;
using Grpc;

public class UserRegisterDaoTests
{
    private readonly Mock<IGrpcClient> mockGrpcClient;
    private readonly UserRegisterDao userRegisterDao;

    public UserRegisterDaoTests()
    {
        mockGrpcClient = new Mock<IGrpcClient>();
        userRegisterDao = new UserRegisterDao(mockGrpcClient.Object);
    }
    
    
    [Fact]
    public async Task verifying_users_email_returns_the_email()
    {
        var userEmailDto = new UserEmailDTO { Email = "test@example.com" };
        mockGrpcClient.Setup(client => client.GetUserByEmail(userEmailDto))
            .ReturnsAsync("test@example.com");

        var result = await userRegisterDao.VerifyUser(userEmailDto);
        
        Assert.Equal("test@example.com", result);
    }
    
    [Fact]
    public async Task registering_the_user_with_valid_data()
    {
        var userRegisterDto = new UserRegisterDto
        {
            Email = "test@example.com",
            Firstname = "TestFirstName",
            Middlename = "",
            Lastname = "TestLastName",
            Password = "12345678",
            Plan = "Premium"
        };
        mockGrpcClient.Setup(client => client.RegisterUser(userRegisterDto))
            .Returns(Task.CompletedTask);

        
        await userRegisterDao.RegisterUser(userRegisterDto);

        mockGrpcClient.Verify(client => client.RegisterUser(userRegisterDto), Times.Once());
    }
    [Fact]
    public async Task getting_user_id_by_email_returns_user_id()
    {
        
        var userEmailDto = new UserEmailDTO { Email = "test@example.com" };
        mockGrpcClient.Setup(client => client.getUserID(userEmailDto))
            .ReturnsAsync(123);
        var result = await userRegisterDao.GetUserId(userEmailDto);

        Assert.Equal(123, result);
    }

    [Fact]
    public async Task CreateUserAccountNumber_Calls_For_Grpc()
    {
        await userRegisterDao.CreateUserAccountNumber(It.IsAny<AccountCreateRequestDto>());
        mockGrpcClient.Verify(g=>g.CreateUserAccountNumber(It.IsAny<AccountCreateRequestDto>()));
    }
    [Fact]
    public async Task VerifyAccountNumber_Calls_For_Grpc()
    {
        await userRegisterDao.VerifyAccountNumber(It.IsAny<TransferRequestDTO>());
        mockGrpcClient.Verify(g=>g.GetAccountNumberByAccountNumber(It.IsAny<TransferRequestDTO>()));
    }

}
