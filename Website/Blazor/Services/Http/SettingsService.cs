using System.Text;
using Newtonsoft.Json;
using Shared.DTOs;
using Shared.Models;
using Domain.DTOs;
using JsonSerializer = System.Text.Json.JsonSerializer;
namespace Blazor.Services.Http;

public class SettingsService : ISettingsService
{
    private readonly HttpClient client = new();

    public async Task UpdateEmail(UserNewEmailDTO userNewEmailDto)
    {
        try
        {
            string updatedEmailJson = JsonSerializer.Serialize(userNewEmailDto);
            StringContent content = new(updatedEmailJson, Encoding.UTF8, "application/json");
            HttpResponseMessage responseMessage = await client.PostAsync("http://localhost:5054/Settings/update/email", content);
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

    public async Task UpdatePassword(UserNewPasswordDTO userNewPasswordDto)
    {
        try
        {
            string updatedPasswordJson = JsonSerializer.Serialize(userNewPasswordDto);
            StringContent content = new(updatedPasswordJson, Encoding.UTF8, "application/json");
            HttpResponseMessage responseMessage = await client.PostAsync("http://localhost:5054/Settings/update/password", content);
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

    public async Task UpdatePlan(UserNewPlanDTO userNewPlanDto)
    {
        try
        {
            string updatedPlanJson = JsonSerializer.Serialize(userNewPlanDto);
            StringContent content = new(updatedPlanJson, Encoding.UTF8, "application/json");
            HttpResponseMessage responseMessage = await client.PostAsync("http://localhost:5054/Settings/update/plan", content);
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