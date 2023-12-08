namespace Domain.Models;

public class Issue
{
    public int Id { get; set; }
    public string Title {get; set; }
    public string Body { get; set; }
    public int Owner { get; set; }
    public DateTime CreationTime { get; set; }
    public bool Flagged { get; set; }
}