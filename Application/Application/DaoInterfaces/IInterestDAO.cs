namespace Application.DaoInterfaces;

public interface IInterestDAO
{
    Task<bool> CreditInterest(string account_id);
    Task<DateTime?> CheckInterest(string account_id);
}