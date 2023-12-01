using Domain.Models;
using Shared.DTOs;

namespace Application.LogicInterfaces;

public interface IAuthLogic
{
    Task<User> Login(UserLoginRequestDto userLoginRequestDto);
    Task<List<AccountsInfo>> GetAccounts();
    Task<List<AccountsInfo>> GetUserAccounts(UserLoginRequestDto userLoginRequestDto);
}