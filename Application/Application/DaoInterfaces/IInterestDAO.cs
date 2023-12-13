using Domain.DTOs;

namespace Application.DaoInterfaces;

public interface IInterestDAO
{
    Task<bool> CreditInterest(CreditInterestDTO dto);
    Task<DateTime?> CheckInterest(InterestCheckDTO dto);
    Task<double> GetBalanceByAccountNumber(InterestCheckDTO interestCheck);
    Task<double> GetInterestRateByAccountNumber(InterestCheckDTO interestCheck);
    
}