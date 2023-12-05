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

            Console.WriteLine(user_id);
            
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


            await settingsDao.ChangeBaseRate(accountNewBaseRateDto);
            Console.WriteLine("coaie");

            await settingsDao.ChangeUserDetails(userNewDetailsRequestDto);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
}