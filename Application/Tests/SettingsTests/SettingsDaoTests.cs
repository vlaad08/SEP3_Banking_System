using Domain.DTOs;
using Grpc;
using Grpc.DAOs;
using Moq;

namespace Tests.SettingsTests;

public class SettingsDaoTests
{
    private readonly Mock<IGrpcClient> grpcClient;
    private readonly SettingsDao settingDao;

    public SettingsDaoTests()
    {
        grpcClient = new Mock<IGrpcClient>();
        settingDao = new SettingsDao(grpcClient.Object);
    }

    [Fact]
    public async void changing_the_base_rate()
    {
        var accountBaseRate = new AccountNewBaseRateDTO()
        {
            UserID = 1,
            BaseRate = 3.7
        };
        grpcClient.Setup(client => client.UpdateBaseRate(accountBaseRate));

        await settingDao.UpdateBaseRate(accountBaseRate);

        grpcClient.Verify(client => client.UpdateBaseRate(accountBaseRate), Times.Once());
    }

    [Fact]
    public async Task changing_the_base_rate_throws_exception()
    {
        var accountBaseRate = new AccountNewBaseRateDTO()
        {
            UserID = 1,
            BaseRate = 3.7
        };

        grpcClient.Setup(client => client.UpdateBaseRate(accountBaseRate)).Throws<Exception>();


        await Assert.ThrowsAsync<Exception>(() => settingDao.UpdateBaseRate(accountBaseRate));
    }

    [Fact]
    public async Task changing_user_details()
    {
        var userDetails = new UserNewDetailsRequestDTO()
        {
            OldEmail = "oldEmail@gmail.com",
            NewEmail = "newEmail@gmail.com",
            Password = "testPassword",
            Plan = "Premium"
        };


        grpcClient.Setup(client => client.ChangeUserDetails(userDetails));

        settingDao.ChangeUserDetails(userDetails);
        
        grpcClient.Verify(client => client.ChangeUserDetails(userDetails), Times.Once);

    }

    [Fact]
    public async Task changing_user_details_throws_exception()
    {
        var userDetails = new UserNewDetailsRequestDTO()
        {
            OldEmail = "oldEmail@gmail.com",
            NewEmail = "newEmail@gmail.com",
            Password = "testPassword",
            Plan = "Premium"
        };
        grpcClient.Setup(client => client.ChangeUserDetails(userDetails)).Throws<Exception>();

        await Assert.ThrowsAsync<Exception>(() => settingDao.ChangeUserDetails(userDetails));
    }

    [Fact]
    public async Task UpdateEmail_Calls_For_Grpc()
    {
        await settingDao.UpdateEmail(It.IsAny<UserNewEmailDTO>());
        grpcClient.Verify(g=>g.UpdateEmail(It.IsAny<UserNewEmailDTO>()));
    }
    [Fact]
    public async Task UpdatePassword_Calls_For_Grpc()
    {
        await settingDao.UpdatePassword(It.IsAny<UserNewPasswordDTO>());
        grpcClient.Verify(g=>g.UpdatePassword(It.IsAny<UserNewPasswordDTO>()));
    }
    [Fact]
    public async Task UpdatePlan_Calls_For_Grpc()
    {
        await settingDao.UpdatePlan(It.IsAny<UserNewPlanDTO>());
        grpcClient.Verify(g=>g.UpdatePlan(It.IsAny<UserNewPlanDTO>()));
    }
}