using System.Text.Json.Serialization;

namespace Domain.Models;

public class User
{
    public string? FirstName { get; set; }
    public string? LastName { get; set; }
    public string? MiddleName { get; set; }
    public string? Email { get; set; }
    public string? Role { get; set; }
    public double? Money { get; set; }
    public string? Password { get; set; }
    public List<AccountsInfo> AccountsInfos { get; set; }


}