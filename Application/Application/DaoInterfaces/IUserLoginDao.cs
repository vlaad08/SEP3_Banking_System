using Shared.DTOs;
using Shared.Models;

namespace Application.DaoInterfaces;

public interface IUserLoginDao
{
    Task<List<User>> GetAllUserDataForValidation();
}