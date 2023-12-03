using Shared.DTOs;

namespace Blazor.Services.Http;

public class IssueService : IIssueService
{
    private readonly HttpClient client = new ();
    public async Task CreateIssue(IssueCreationDto dto)
    {
        try
        {
            Console.WriteLine(dto.Title+" "+dto.Body+" "+dto.Owner);
            HttpResponseMessage response = await client.PostAsJsonAsync("https://localhost:7257/Issue/Issue", dto);
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
}