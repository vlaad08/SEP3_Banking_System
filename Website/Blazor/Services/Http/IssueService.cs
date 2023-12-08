using System.Text.Json;
using Newtonsoft.Json;
using Shared.DTOs;
using Shared.Models;
using System.Text.Json.Serialization;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace Blazor.Services.Http;

public class IssueService : IIssueService
{
    private readonly HttpClient client = new();
    public async Task CreateIssue(IssueCreationDto dto)
    {
        try
        {
            HttpResponseMessage response = await client.PostAsJsonAsync("http://localhost:5054/Issue/Issue", dto);
            string responseBody = await response.Content.ReadAsStringAsync();
            if (!response.IsSuccessStatusCode)
            {
                throw new Exception(responseBody);
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
            throw new Exception(e.Message);
        }
    }

    public async Task UpdateIssue(IssueUpdateDto dto)
    {
        try
        {
            HttpResponseMessage response = await client.PatchAsJsonAsync("https://localhost:7257/Issue/Issue", dto);
            string responseBody = await response.Content.ReadAsStringAsync();
            if (!response.IsSuccessStatusCode)
            {
                throw new Exception(responseBody);
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
            throw new Exception(e.Message);
        }
    }

    public async Task SendMessage(SendMessageDto dto)
    {
        try
        {
            HttpResponseMessage response = await client.PostAsJsonAsync("http://localhost:5054/Issue/Message", dto);
            string responseBody = await response.Content.ReadAsStringAsync();
            if (!response.IsSuccessStatusCode)
            {
                throw new Exception(responseBody);
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
            throw new Exception(e.Message);
        }
    }

    public async Task<IEnumerable<Message>> GetMessagesForIssue(GetMessagesDto getMessagesDto)
    {
        try
        {
            HttpResponseMessage response = await client.GetAsync($"http://localhost:5054/Issue/Message/{getMessagesDto.Id}");
            string responseBody = await response.Content.ReadAsStringAsync();
            if (!response.IsSuccessStatusCode)
            {
                throw new Exception(responseBody);
            }

            IEnumerable<Message> messages = JsonSerializer.Deserialize<IEnumerable<Message>>(responseBody, new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true
            })!;
            return messages;
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
            throw new Exception(e.Message);
        }

    }

    public async Task<IEnumerable<Issue>> GetIssues()
    {
        try
        {
            HttpResponseMessage response = await client.GetAsync("http://localhost:5054/Issue/Employee/Issues");
            string responseBody = await response.Content.ReadAsStringAsync();
            if (!response.IsSuccessStatusCode)
            {
                throw new Exception(responseBody);
            }

            IEnumerable<Issue> issues = JsonSerializer.Deserialize<IEnumerable<Issue>>(responseBody, new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true
            })!;
            return issues;
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
            throw new Exception(e.Message);
        }
    }

    public async Task<IEnumerable<Issue>> GetIssuesForClient(GetIssuesDto dto)
    {
        try
        {
            int userId = dto.UserId;
            HttpResponseMessage response = await client.GetAsync($"http://localhost:5054/Issue/Client/Issues/{userId}");
            string responseBody = await response.Content.ReadAsStringAsync();
            if (!response.IsSuccessStatusCode)
            {
                throw new Exception(responseBody);
            }

            IEnumerable<Issue> issues = JsonSerializer.Deserialize<IEnumerable<Issue>>(responseBody, new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true
            })!;
            return issues;
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
            throw new Exception(e.Message);
        }
    }
}