namespace Domain.DTOs;

public class ExportRequestDTO
{
    public string Email { get; init; }
    public DateTime StartDate { get; init; }
    public DateTime EndDate { get; init; }
}