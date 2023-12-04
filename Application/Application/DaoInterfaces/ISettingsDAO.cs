using Domain.DTOs;

namespace Application.DaoInterfaces;

public interface ISettingsDAO
{
    Task ChangeBaseRate(AccountNewBaseRateDTO accountNewBaseRateDto);
    Task ChangeUserDetails(UserNewDetailsRequestDTO userNewDetailsRequestDto);
}