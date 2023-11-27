using Domain.Models;
using Shared.DTOs;

namespace Application.LogicInterfaces;

public interface IAuthLogic
{
    Task<User> Login(UserLoginRequestDto userLoginRequestDto);
}