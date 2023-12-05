namespace Domain.DTOs;

public class IssueCreationDTO
{
    public string Title {get; set; }
    public string Body { get; set; }
    public int Owner { get; set; }
}