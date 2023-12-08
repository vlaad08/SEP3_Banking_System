using Application.DaoInterfaces;
using Application.LogicInterfaces;
using Domain.DTOs;
using Grpc.DAOs;

namespace Application.Logic;

public class SettingsLogic : ISettingsLogic
{
    private readonly ISettingsDAO settingsDao;
    private readonly IUserRegisterDAO registerDao;
    private readonly IUserLoginDao userLoginDao;

    public SettingsLogic(ISettingsDAO settingsDao, IUserRegisterDAO registerDao,IUserLoginDao userLoginDao)
    {
        this.settingsDao = settingsDao;
        this.registerDao = registerDao;
        this.userLoginDao = userLoginDao;
    }

    public async Task UpdateUser(UserNewDetailsRequestDTO userNewDetailsRequestDto)
    {
        try
        {
            UserEmailDTO userEmailDto = new UserEmailDTO()
            {
                Email = userNewDetailsRequestDto.OldEmail
            };
            int user_id = await registerDao.GetUserId(userEmailDto);

            
            double newBaseRate = 1.7;
            if (userNewDetailsRequestDto.Plan == "Premium")
            {
                newBaseRate = 3.7;
            }

            AccountNewBaseRateDTO accountNewBaseRateDto = new AccountNewBaseRateDTO()
            {
                UserID = user_id,
                BaseRate = newBaseRate
            };


            await settingsDao.UpdateBaseRate(accountNewBaseRateDto);

            await settingsDao.ChangeUserDetails(userNewDetailsRequestDto);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    public async Task UpdateEmail(UserNewEmailDTO userNewEmailDto)
    {
        await settingsDao.UpdateEmail(userNewEmailDto);
    }

    public async Task UpdatePassword(UserNewPasswordDTO userNewPasswordDto)
    {
        await settingsDao.UpdatePassword(userNewPasswordDto);
    }

    public async Task UpdatePlan(UserNewPlanDTO userNewPlanDto)
    {
        double newBaseRate = 1.7;
        if (userNewPlanDto.Plan == "Premium")
        {
            newBaseRate = 3.7;
        }
        AccountNewBaseRateDTO accountNewBaseRateDto = new AccountNewBaseRateDTO()
        {
            UserID = userNewPlanDto.UserID,
            BaseRate = newBaseRate
        };

        await settingsDao.UpdateBaseRate(accountNewBaseRateDto);
        await settingsDao.UpdatePlan(userNewPlanDto);
    }
}