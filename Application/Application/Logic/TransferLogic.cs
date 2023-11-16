using Application.DaoInterfaces;
using Application.LogicInterfaces;
using Domain.DTOs;
using Domain.Models;

namespace Application.Logic;

public class TransferLogic : ITransferLogic
{
    //ADD ASYNC!!!!!
    private readonly ITransferDAO transferDao;

    public TransferLogic(ITransferDAO transferDAO)
    {
        this.transferDao = transferDAO;
    }

    private Task<bool> ValidateTransfer(TransferRequestDTO transferRequestDto)
    {
        if (transferDao.GetBalanceByAccountNumber(transferRequestDto.SenderAccountNumber).Result >=
            transferRequestDto.Amount &&
            transferDao.GetAccountNumberByAccountNumber(transferRequestDto.RecipientAccountNumber).Result
                .Equals(transferRequestDto.RecipientAccountNumber))
        {
            return Task.FromResult(true);
        }
        else
        {
            return Task.FromResult(false);
        }
    }

    public async Task TransferMoney(TransferRequestDTO transferRequest)
    {
        if (ValidateTransfer(transferRequest).Result)
        {
            await transferDao.TransferMoney(transferRequest);
        }
    }
    
    
}