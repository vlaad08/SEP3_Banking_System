namespace Shared.DAO;

public class TransactionDao
{
    public string SenderName { get; set; }
    public string RecipientName { get; set; }
    public string SenderAccountNumber { get; set; }
    public string RecipientAccountNumber { get; set; }
    public double Amount { get; set; }
    public string Message { get; set; }
    public DateTime Date { get; set; }
    public string transactionType { get; set; }
}