namespace Shared.DTOs;
public class SendMessageDto
{
    public string Title { get; set; }
    public string Body { get; set; }
    public int Owner { get; set; }
    public int IssueId { get; set; }

    public SendMessageDto(string title, string body, int owner, int issueId)
    {
        Title = title;
        Body = body;
        Owner = owner;
        IssueId = issueId;
    }
}