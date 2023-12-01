using Domain.DTOs;

namespace Application.LogicInterfaces;

public interface IDepositLogic
{
    Task DepositMoney(DepositRequestDTO depositRequestDto);
}