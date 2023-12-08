using Domain.DTOs;

namespace Application.DaoInterfaces;

public interface IDepositDAO
{
    Task DepositMoney(UpdatedDepositDTO updatedDepositDto);
    Task<double> GetBalanceByAccountNumber(DepositRequestDTO updatedDepositDto);
}