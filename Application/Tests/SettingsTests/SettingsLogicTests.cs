using Application.DaoInterfaces;
using Application.Logic;
using Domain.DTOs;
using Grpc.DAOs;
using Moq;

namespace Tests.SettingsTests;

public class SettingsLogicTests
{
    private readonly SettingsLogic settingsLogic;
    private readonly Mock<IUserRegisterDAO> registerDao;
    private readonly Mock<ISettingsDAO> settingsDao;


    public SettingsLogicTests()
    {
        settingsDao = new Mock<ISettingsDAO>();
        registerDao = new Mock<IUserRegisterDAO>();
        settingsLogic = new SettingsLogic(settingsDao.Object, registerDao.Object);
    }

    [Fact]
    public async void update_user()
    {
        UserEmailDTO userEmailDto = new UserEmailDTO()
        {
            Email = "oldEmail@gmail.com"
        };

        registerDao.Setup(dao => dao.GetUserId(userEmailDto)).ReturnsAsync(1);
        var result = await registerDao.Object.GetUserId(userEmailDto);
        Assert.Equal(1, result);

        AccountNewBaseRateDTO rateDto = new AccountNewBaseRateDTO()
        {
            UserID = result,
            BaseRate = 3.7
        };

        UserNewDetailsRequestDTO userNewDetailsRequestDto = new UserNewDetailsRequestDTO()
        {
            NewEmail = "newEmail@gmail.com",
            OldEmail = userEmailDto.Email,
            Password = "1234567",
            Plan = "Premium"
        };

        settingsDao.Setup(dao => dao.ChangeBaseRate(rateDto));
        settingsDao.Setup(dao => dao.ChangeUserDetails(userNewDetailsRequestDto));

        await settingsLogic.UpdateUser(userNewDetailsRequestDto);
        
    }
}