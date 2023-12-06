namespace Shared.DTOs;

public class IssueUpdateDto
{
    public IssueUpdateDto(int id)
    {
        Id = id;
    }
    public int Id { get; set; }
}