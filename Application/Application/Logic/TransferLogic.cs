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

    private bool ValidateTransfer(TransferRequestDTO transferRequest)
    {   //mybe async?
        if (transferDao.GetBalanceByAccountNumber(transferRequest.SenderAccountNumber) >= transferRequest.Amount &&
            transferDao.GetAccountNumberByAccountNumber(transferRequest.RecipientAccountNumber)
                .Equals(transferRequest.RecipientAccountNumber))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public async Task TransferMoney(TransferRequestDTO transferRequest)
    {
        if (ValidateTransfer(transferRequest))
        {
           transferDao.TransferMoney(transferRequest);
        }
    }
    
    
}