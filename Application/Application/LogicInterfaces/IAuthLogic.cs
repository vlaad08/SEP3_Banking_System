using Shared.DTOs;
using Shared.Models;

namespace WebAPI.Services;

public interface IAuthLogic
{
    Task<User> Login(UserLoginRequestDto userLoginRequestDto);
}