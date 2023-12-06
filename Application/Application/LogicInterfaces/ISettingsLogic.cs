using Domain.DTOs;

namespace Application.LogicInterfaces;

public interface ISettingsLogic
{
    Task UpdateUser(UserNewDetailsRequestDTO userNewDetailsRequestDto);

    Task UpdateEmail(UserNewEmailDTO userNewEmailDto);
    Task UpdatePassword(UserNewPasswordDTO userNewPasswordDto);
    Task UpdatePlan(UserNewPlanDTO userNewPlanDto);
}