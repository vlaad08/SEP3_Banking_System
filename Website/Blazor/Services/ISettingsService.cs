using Domain.DTOs;

namespace Blazor.Services;

public interface ISettingsService
{
    Task UpdateEmail(UserNewEmailDTO userNewEmailDto);
    Task UpdatePassword(UserNewPasswordDTO userNewPasswordDto);
    Task UpdatePlan(UserNewPlanDTO userNewPlanDto);
}