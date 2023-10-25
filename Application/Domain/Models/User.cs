namespace Domain.Models;


public class User
{
    //not much done for the user but i need to have the object cuz the transfers have senders and recipients
    public int UserId { get; set; }
    public string UserName { get; set; }
    public string AccountNumber { get; set; }
    public decimal Balance { get; set; }
    
    public User(int userId, string userName, string accountNumber, decimal balance)
    {
        UserId = userId;
        UserName = userName;
        AccountNumber = accountNumber;
        Balance = balance;
    }
}
