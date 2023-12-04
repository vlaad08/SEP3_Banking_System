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
    public async Task VerifyUser_ValidEmail_ReturnsEmail()
    {
        var userEmailDto = new UserEmailDTO { Email = "test@example.com" };
        mockGrpcClient.Setup(client => client.GetUserByEmail(userEmailDto))
            .ReturnsAsync("test@example.com");

        var result = await userRegisterDao.VerifyUser(userEmailDto);
        
        Assert.Equal("test@example.com", result);
    }
    
    [Fact]
    public async Task RegisterUser_ValidData_RegistersUser()
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
    public async Task GetUserId_ExistingUser_ReturnsUserId()
    {
        
        var userEmailDto = new UserEmailDTO { Email = "test@example.com" };
        mockGrpcClient.Setup(client => client.getUserID(userEmailDto))
            .ReturnsAsync(123);
        var result = await userRegisterDao.GetUserId(userEmailDto);

        Assert.Equal(123, result);
    }

}
