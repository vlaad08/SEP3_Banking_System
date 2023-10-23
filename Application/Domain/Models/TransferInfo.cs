namespace Domain.Models;

public class TransferInfo
{
    public int Id { get; set; }
    public decimal Amount { get; set; }
    public string RecipientName { get; set; }
    public string RecipientAccountNumber { get; set; }
    public string Message { get; set; }
    
    public TransferInfo(decimal amount, string recipientName, string recipientAccountNumber, string message)
    {
        Amount = amount;
        RecipientName = recipientName;
        RecipientAccountNumber = recipientAccountNumber;
        Message = message;
    }
}
