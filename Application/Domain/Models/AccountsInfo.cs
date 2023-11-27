namespace Domain.Models;

public class AccountsInfo
{
    public string AccountNumber { get; set; }
    public string AccountOwner { get; set; }
    public double Balance { get; set; }
    public string AccountType { get; set; }
    
    public AccountsInfo(string accountNumber, string accountOwner, double balance, string accountType)
    {
        AccountNumber = accountNumber;
        AccountOwner = accountOwner;
        Balance = balance;
        AccountType = accountType;
    }
}