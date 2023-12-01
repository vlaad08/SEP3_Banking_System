namespace Shared.DAO;

public class TransactionDao
{
    //sender recipient amount message date
    public string SenderAccountNumber { get; set; }
    public string RecipientAccountNumber { get; set; }
    public double Amount { get; set; }
    public string Message { get; set; }
    public DateTime Date { get; set; }
}