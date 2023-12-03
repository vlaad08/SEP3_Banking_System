using Application.DaoInterfaces;
using Domain.DTOs;
using Grpc;
using Grpc.DAOs;
using Moq;
using WebAPI.Services;

namespace Tests.UserRegisterTests;

public class UserRegisterLogicTests
{
    
    private readonly Mock<IUserLoginDao> mockUserLoginDao;
    private readonly Mock<IInterestDAO> mockInterestDao;
    private readonly Mock<IUserRegisterDAO> mockRegisterDao;
    private readonly AuthLogic authLogic;

    public UserRegisterLogicTests()
    {
        mockUserLoginDao = new Mock<IUserLoginDao>();
        mockInterestDao = new Mock<IInterestDAO>();
        mockRegisterDao = new Mock<IUserRegisterDAO>();
        authLogic = new AuthLogic(mockUserLoginDao.Object, mockInterestDao.Object, mockRegisterDao.Object);
    }
    
    
    [Fact]
    public async Task VerifyUser_WithExistingEmail_ReturnsTrue()
    {
        
        var userRegisterDto = new UserRegisterDto { Email = "existing@example.com" };
        mockRegisterDao.Setup(dao => dao.VerifyUser(It.IsAny<UserEmailDTO>()))
            .ReturnsAsync("existing@example.com");

        var result = await authLogic.VerifyUser(userRegisterDto);

        
        Assert.True(result);
    }

    [Fact]
    public async Task VerifyUser_WithNonExistingEmail_ReturnsFalse()
    {
        // Arrange similar to above, but return a non-matching email or null
    }
    
    [Fact]
    public async Task RegisterUser_CallsRegisterDaoWithValidData()
    { 
        var userRegisterDto = new UserRegisterDto { /* Populate with test data */ };
        mockRegisterDao.Setup(dao => dao.RegisterUser(userRegisterDto)).Returns(Task.CompletedTask);

        await authLogic.RegisterUser(userRegisterDto);

        mockRegisterDao.Verify(dao => dao.RegisterUser(userRegisterDto), Times.Once());
    }
    
    [Fact]
    public async Task GetUserId_WithValidEmail_ReturnsUserId()
    {
        
        var userEmailDto = new UserEmailDTO { Email = "test@example.com" };
        mockRegisterDao.Setup(dao => dao.GetUserId(userEmailDto)).ReturnsAsync(1); // Assuming 1 is the user ID

        var result = await authLogic.GetUserId(userEmailDto);

        Assert.Equal(1, result);
    }
    
    [Fact]
    public async Task VerifyAccountNumber_WithValidAccount_ReturnsTrue()
    {
        var transferRequestDto = new TransferRequestDTO { /* Populate with test data */ };
        mockRegisterDao.Setup(dao => dao.VerifyAccountNumber(transferRequestDto)).ReturnsAsync("valid_account_number");

        
        var result = await authLogic.VerifyAccountNumber(transferRequestDto);

        Assert.True(result);
    } 

    [Fact]
    public async Task VerifyAccountNumber_WithInvalidAccount_ReturnsFalse()
    {
        // Arrange with an invalid account number
    }
    
    
    [Fact]
    public async Task CreateUserAccountNumber_CallsRegisterDaoWithValidData()
    {
        
        var accountCreateRequestDto = new AccountCreateRequestDto {  };
        mockRegisterDao.Setup(dao => dao.CreateUserAccountNumber(accountCreateRequestDto)).Returns(Task.CompletedTask);

        
        await authLogic.CreateUserAccountNumber(accountCreateRequestDto);

        
        mockRegisterDao.Verify(dao => dao.CreateUserAccountNumber(accountCreateRequestDto), Times.Once());
    }
}