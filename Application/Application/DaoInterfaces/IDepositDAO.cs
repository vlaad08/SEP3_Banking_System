using Domain.DTOs;

namespace Application.DaoInterfaces;

public interface IDepositDAO
{
    Task DepositMoney(DepositRequestDTO depositRequestDto);
}