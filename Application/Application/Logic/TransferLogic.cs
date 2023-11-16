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
        if (transferDao.GetAccountNumberByAccountNumber(transferRequestDto.RecipientAccountNumber).Result.Equals(transferRequestDto.RecipientAccountNumber))
        {
            if (transferDao.GetBalanceByAccountNumber(transferRequestDto.SenderAccountNumber).Result >= transferRequestDto.Amount)
            {
                return Task.FromResult(true);
            }
            else
            {
                throw new Exception("There is not sufficient balance to make the transaction!");
            }
        }
        else
        {
            throw new Exception("The account number does not exist!");
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