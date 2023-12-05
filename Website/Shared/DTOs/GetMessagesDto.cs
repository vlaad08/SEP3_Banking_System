namespace Shared.DTOs;

public class GetMessagesDto
{
    public int Id { get; set; }

    public GetMessagesDto(int id)
    {
        Id = id;
    }
}