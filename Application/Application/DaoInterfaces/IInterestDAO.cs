using Domain.DTOs;

namespace Application.DaoInterfaces;

public interface IInterestDAO
{
    Task<bool> CreditInterest(InterestCheckDTO dto);
    Task<DateTime?> CheckInterest(InterestCheckDTO dto);
}