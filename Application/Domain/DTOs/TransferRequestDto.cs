namespace Domain.DTOs;

public class TransferRequestDTO
{
    public decimal Amount { get; set; }
    public int RecipientName { get; set; }
    public int RecipientAccountNumber { get; set; }
    public string Message { get; set; }
    public string SenderAccountNumber { get; set; }
}