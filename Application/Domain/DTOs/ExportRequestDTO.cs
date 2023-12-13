namespace Domain.DTOs;

public class ExportRequestDTO
{
    public string Email { get; set; }
    public DateTime StartDate { get; set; }
    public DateTime EndDate { get; set; }
}