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
        
    }

    public async Task DepositMoney(DepositRequestDTO depositRequestDto)
    {
        await ValidateDeposit(depositRequestDto);
        await depositDao.DepositMoney(depositRequestDto);
    }
}