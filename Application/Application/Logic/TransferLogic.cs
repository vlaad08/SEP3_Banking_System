using System.Security.AccessControl;
using System.Threading.Channels;
using Application.DaoInterfaces;
using Application.LogicInterfaces;
using Domain.DTOs;
using Domain.Models;

namespace Application.Logic;

public class TransferLogic : ITransferLogic
{
    private readonly ITransferDAO transferDao;

    public TransferLogic(ITransferDAO transferDAO)
    {
        this.transferDao = transferDAO;
    }

    private async Task ValidateTransfer(TransferRequestDTO transferRequestDto)
    {
        if (await transferDao.GetAccountNumberByAccountNumber(transferRequestDto.RecipientAccountNumber) != transferRequestDto.RecipientAccountNumber)
        {
            throw new Exception("The account number does not exist!");
        }

        if (await transferDao.GetBalanceByAccountNumber(transferRequestDto.SenderAccountNumber) < transferRequestDto.Amount)
        {
            throw new Exception("There is not sufficient balance to make the transaction!");
        }

        if ((await transferDao.GetTransferAmountsByDayForUser(transferRequestDto.SenderAccountNumber)+transferRequestDto.Amount)>= 200000)
        {
            throw new Exception("You have reached your daily limit!");
        }
    }   

    public async Task TransferMoney(TransferRequestDTO transferRequest)
    {
        await ValidateTransfer(transferRequest);
        await transferDao.TransferMoney(transferRequest);
    }

    
    
}