namespace Domain.DTOs;

public class TransferRequestDTO
{
    public decimal Amount { get; set; }
    public string RecipientName { get; set; }
    public string RecipientAccountNumber { get; set; }
    public string Message { get; set; }
}