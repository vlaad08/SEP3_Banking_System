using Domain.DTOs;

namespace Application.LogicInterfaces;

public interface ISettingsLogic
{
    Task UpdateUser(UserNewDetailsRequestDTO userNewDetailsRequestDto);
}