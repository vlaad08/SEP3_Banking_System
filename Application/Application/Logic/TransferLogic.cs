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

    public async Task TransferMoney(TransferRequestDTO transferRequest)
    {
        //TransferValidation.ValidateRequest(transferRequest);
        
        //transfer logic should happen here
        //it tells the server to reach db and use ig a change balance method to change
        //balance by transfer amount on the two accounts
        //public void changeBalance(int accNumber, int amount) and then amount could be negative
        transferDao.TransferMoney(transferRequest);
    }
    
    
}