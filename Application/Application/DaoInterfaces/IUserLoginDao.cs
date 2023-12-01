using Domain.Models;
using Shared.DTOs;

namespace Application.DaoInterfaces;

public interface IUserLoginDao
{
    Task<List<User>> GetAllUserDataForValidation();
    Task<List<AccountsInfo>> GetAccounts();
    Task<List<AccountsInfo>> GetUserAccounts(UserLoginRequestDto dto);
}