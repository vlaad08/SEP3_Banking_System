namespace Domain.Models;

public class Message
{
    public string Title { get; set; }
    public string Body { get; set; }
    public int Owner { get; set; }
    public DateTime CreationTime { get; set; }
}