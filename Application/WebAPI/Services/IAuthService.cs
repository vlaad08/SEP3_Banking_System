using Shared.Models;

namespace WebAPI.Services;

public interface IAuthService
{
    Task RegisterUser(User user);
    Task<User> ValidateUser(string? email, string? password);
}