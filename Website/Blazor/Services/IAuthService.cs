using System.Security.Claims;
using Shared.DTOs;
using Shared.Models;

namespace Blazor.Services;

public interface IAuthService
{
    public Task LoginAsync(string email, string password);
    public Task LogoutAsync();
    public Task RegisterAsync(UserRegisterDto user);
    public Task<ClaimsPrincipal> GetAuthAsync();

    public Action<ClaimsPrincipal> OnAuthStateChanged { get; set; }
    
}