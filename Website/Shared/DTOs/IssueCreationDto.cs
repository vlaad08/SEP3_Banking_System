namespace Shared.DTOs;

public class IssueCreationDto
{
    public string Title {get; set; }

    public string Body { get; set; }

    public int Owner { get; set; }
    public IssueCreationDto(string title, string body, int owner)
    {
        Title = title;
        Body = body;
        Owner = owner;
    }
}