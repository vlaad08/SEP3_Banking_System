using Domain.DTOs;

namespace Application.DaoInterfaces;

public interface ISettingsDAO
{
    Task UpdateBaseRate(AccountNewBaseRateDTO accountNewBaseRateDto);
    Task ChangeUserDetails(UserNewDetailsRequestDTO userNewDetailsRequestDto);

    Task UpdateEmail(UserNewEmailDTO userNewEmailDto);
    Task UpdatePassword(UserNewPasswordDTO userNewPasswordDto);
    Task UpdatePlan(UserNewPlanDTO userNewPlanDto);
    
}