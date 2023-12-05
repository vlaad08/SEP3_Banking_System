namespace Domain.DTOs;

public class IssueGetterDTO
{
    public int Id { get; set; }

    public IssueGetterDTO(int id)
    {
        Id = id;
    }
}