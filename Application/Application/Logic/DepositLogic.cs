using Application.DaoInterfaces;
using Application.LogicInterfaces;
using Domain.DTOs;

namespace Application.Logic;

public class DepositLogic : IDepositLogic
{
    private readonly IDepositDAO depositDao;

    public DepositLogic(IDepositDAO depositDao)
    {
        this.depositDao = depositDao;
    }

    public async Task ValidateDeposit(DepositRequestDTO depositRequestDto)
    {
        //Defend
    }

    public async Task DepositMoney(DepositRequestDTO depositRequestDto)
    {
        await ValidateDeposit(depositRequestDto);
        double oldBalance = await depositDao.GetBalanceByAccountNumber(depositRequestDto);
        double newBalance = oldBalance + depositRequestDto.Amount;
        UpdatedDepositDTO dto = new UpdatedDepositDTO
        {
            AccountId = depositRequestDto.ToppedUpAccountNumber,
            Amount = depositRequestDto.Amount,
            UpdatedBalance = newBalance
        };
        await depositDao.DepositMoney(dto);
    }
}