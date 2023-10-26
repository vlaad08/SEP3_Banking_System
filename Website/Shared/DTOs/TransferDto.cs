namespace Shared.DTOs;

public class TransferDto
{
    public double Amount { get; set; }
    public string SenderAccountNumber { get; set; }
    public string Message { get; set; }
    public string RecipientAccountNumber { get; set; }
}