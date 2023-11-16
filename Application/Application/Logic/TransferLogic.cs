using System.Security.AccessControl;
using System.Threading.Channels;
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

    private async Task ValidateTransfer(TransferRequestDTO transferRequestDto)
    {
        if (await transferDao.GetAccountNumberByAccountNumber(transferRequestDto.RecipientAccountNumber) != transferRequestDto.RecipientAccountNumber)
        {
            throw new Exception("The account number does not exist!");
        }

        if (await transferDao.GetBalanceByAccountNumber(transferRequestDto.SenderAccountNumber) < transferRequestDto.Amount)
        {
            Console.WriteLine(transferDao.GetBalanceByAccountNumber(transferRequestDto.SenderAccountNumber));
            throw new Exception("There is not sufficient balance to make the transaction!");
        }
    }   

    public async Task TransferMoney(TransferRequestDTO transferRequest)
    {
        await ValidateTransfer(transferRequest);
        await transferDao.TransferMoney(transferRequest);
    }

    
    
}