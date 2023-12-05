using System.Security.Claims;
using System.Text;
using Newtonsoft.Json;
using Shared.DAO;
using Shared.DTOs;
using Shared.Models;
using JsonSerializer = System.Text.Json.JsonSerializer;
namespace Blazor.Services.Http;

public class SettingsService : ISettingsService
{
    private readonly HttpClient client = new ();

    public async Task UpdateUserDetails(string newEmail, string oldEmail, string password, string plan)
    {
        UserNewDetailsDto userdto = new UserNewDetailsDto()
        {
            NewEmail = newEmail,
            OldEmail = oldEmail,
                Password= password,
                Plan = plan
        };
        try
        {
            string updateuserjson = JsonSerializer.Serialize(userdto);
            StringContent content = new(updateuserjson, Encoding.UTF8, "application/json");
            HttpResponseMessage responseMessage=await client.PostAsync("http://localhost:5054/Settings/updateUser", content);
            string responseBody = await responseMessage.Content.ReadAsStringAsync();
            if (!responseMessage.IsSuccessStatusCode)
            {
                throw new Exception(responseBody);
            }

            Console.WriteLine("Settings work");
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
            throw;
        }

    }
}