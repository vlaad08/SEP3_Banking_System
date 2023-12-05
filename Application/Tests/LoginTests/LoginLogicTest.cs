using Application.DaoInterfaces;
using Domain.DTOs;
using Domain.Models;
using Moq;
using Shared.DTOs;
using WebAPI.Services;

namespace Tests;

public class LoginLogicTest
{
    [Fact]
    public async Task GetAccounts_Calls_LoginDao()
    {
        var loginDao = new Mock<IUserLoginDao>();
        var interestDao = new Mock<IInterestDAO>();
        var registerDao = new Mock<IUserRegisterDAO>();
        var auth = new AuthLogic(loginDao.Object, interestDao.Object, registerDao.Object);
        await auth.GetAccounts();
        loginDao.Verify(d => d.GetAccounts());
    }

    [Fact]
    public async Task GetUserAccounts_Calls_LoginDao()
    {
        UserLoginRequestDto dto = new UserLoginRequestDto
        {
            Email = "user1@gmail.com",
            Password = "password1"
        };
        var loginDao = new Mock<IUserLoginDao>();
        var interestDao = new Mock<IInterestDAO>();
        var registerDao = new Mock<IUserRegisterDAO>();
        var auth = new AuthLogic(loginDao.Object, interestDao.Object, registerDao.Object);
        loginDao.Setup(d => d.GetUserAccounts(dto))
            .ReturnsAsync(new List<AccountsInfo>());
        await auth.GetUserAccounts(dto);
        loginDao.Verify(d =>d.GetUserAccounts(dto));
    }

    [Fact]
    public async Task GetUserAccounts_Calls_For_CheckInterest()
    {
        UserLoginRequestDto dto = new UserLoginRequestDto
        {
            Email = "user1@gmail.com",
            Password = "password1"
        };
        var loginDao = new Mock<IUserLoginDao>();
        var interestDao = new Mock<IInterestDAO>();
        var registerDao = new Mock<IUserRegisterDAO>();
        var auth = new AuthLogic(loginDao.Object, interestDao.Object, registerDao.Object);
        loginDao.Setup(d => d.GetUserAccounts(dto))
            .ReturnsAsync(new List<AccountsInfo>
            {
                new AccountsInfo
                {
                    AccountNumber = "123456789",
                    AccountOwner = "Test",
                    Balance = 1000.5,
                    AccountType = "personal"
                }
            });
        interestDao.Setup(i => i.CheckInterest(It.IsAny<InterestCheckDTO>()))
            .ReturnsAsync(DateTime.Now);
        await auth.GetUserAccounts(dto);
        interestDao.Verify( i => i.CheckInterest(It.IsAny<InterestCheckDTO>()));
    }
    
    [Fact]
    public async Task GetUserAccounts_Calls_For_CreditInterest() //This test was written (and passed) on 1/12, and the CreditInterest has been called, meaning the method will grant the interest if it is the first day of the month today
    {
        UserLoginRequestDto dto = new UserLoginRequestDto
        {
            Email = "user1@gmail.com",
            Password = "password1"
        };
        var loginDao = new Mock<IUserLoginDao>();
        var interestDao = new Mock<IInterestDAO>();
        var registerDao = new Mock<IUserRegisterDAO>();
        var auth = new AuthLogic(loginDao.Object, interestDao.Object, registerDao.Object);
        loginDao.Setup(d => d.GetUserAccounts(dto))
            .ReturnsAsync(new List<AccountsInfo>
            {
                new AccountsInfo
                {
                    AccountNumber = "123456789",
                    AccountOwner = "Test",
                    Balance = 1000.5,
                    AccountType = "personal"
                }
            });
        //DateTime today = new DateTime(2023, 11, 1);
        DateTime interestTimestamp = new DateTime(2022, 12, 1);
        interestDao.Setup(i => i.CheckInterest(It.IsAny<InterestCheckDTO>()))
            .ReturnsAsync(interestTimestamp);

        await auth.GetUserAccounts(dto);

        interestDao.Verify(i => i.CreditInterest(It.IsAny<InterestCheckDTO>()), Times.Once);
    }
    
    [Fact]
    public async Task Login_Calls_For_Validation()
    {
        UserLoginRequestDto dto = new UserLoginRequestDto
        {
            Email = "user1@gmail.com",
            Password = "password1"
        };
        List<User> userList = new List<User>
        {
            new User { Email = "user1@gmail.com", Password = "password1" },
            new User { Email = "user2@gmail.com", Password = "password2" }
        };
        var loginDao = new Mock<IUserLoginDao>();
        loginDao.Setup(l => l.GetAllUserDataForValidation())
            .ReturnsAsync(userList);
        var interestDao = new Mock<IInterestDAO>();
        var registerDao = new Mock<IUserRegisterDAO>();
        var auth = new AuthLogic(loginDao.Object, interestDao.Object, registerDao.Object);

        User existing = await auth.Login(dto);
        
        loginDao.Verify(d => d.GetAllUserDataForValidation());
        Assert.NotNull(existing);
    }

    [Fact]
    public async Task Login_Returns_Null_User_Upon_Denied_UserName()
    {
        UserLoginRequestDto dto = new UserLoginRequestDto
        {
            Email = "test@gmail.com",
            Password = "password1"
        };
        List<User> userList = new List<User>
        {
            new User { Email = "user1@gmail.com", Password = "password1" },
            new User { Email = "user2@gmail.com", Password = "password2" }
        };
        var loginDao = new Mock<IUserLoginDao>();
        loginDao.Setup(l => l.GetAllUserDataForValidation())
            .ReturnsAsync(userList);
        var interestDao = new Mock<IInterestDAO>();
        var registerDao = new Mock<IUserRegisterDAO>();
        var auth = new AuthLogic(loginDao.Object, interestDao.Object, registerDao.Object);

        User nonExistent = await auth.Login(dto);
        
        loginDao.Verify(d => d.GetAllUserDataForValidation());
        Assert.Null(nonExistent);
    }
    
    [Fact]
    public async Task Login_Returns_Null_User_Upon_Denied_Password()
    {
        UserLoginRequestDto dto = new UserLoginRequestDto
        {
            Email = "user1@gmail.com",
            Password = "12345678"
        };
        List<User> userList = new List<User>
        {
            new User { Email = "user1@gmail.com", Password = "password1" },
            new User { Email = "user2@gmail.com", Password = "password2" }
        };
        var loginDao = new Mock<IUserLoginDao>();
        var interestDao = new Mock<IInterestDAO>();
        loginDao.Setup(l => l.GetAllUserDataForValidation())
            .ReturnsAsync(userList);
        var registerDao = new Mock<IUserRegisterDAO>();
        var auth = new AuthLogic(loginDao.Object, interestDao.Object, registerDao.Object);

        User nonExistent = await auth.Login(dto);
        
        loginDao.Verify(d => d.GetAllUserDataForValidation());
        Assert.Null(nonExistent);
    }
}