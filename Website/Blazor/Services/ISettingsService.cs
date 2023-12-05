namespace Blazor.Services;

public interface ISettingsService
{
    public Task UpdateUserDetails(string newEmail, string oldEmail, string password, string plan);
}