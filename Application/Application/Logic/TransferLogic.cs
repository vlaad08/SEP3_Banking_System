using Application.DaoInterfaces;
using Application.LogicInterfaces;
using Domain.DTOs;

namespace Application.Logic;

public class TransferLogic : ITransferLogic
{
    //ADD ASYNC!!!!!
    private readonly ITransferDAO _transferDTO;
    public IDataBaseAccess dataBase;

    public TransferLogic(ITransferDAO transferDTO)
    {
        _transferDTO = transferDTO;
    }

    public TransferResultDTO TransferMoney(TransferRequestDTO transferRequest)
    {
        //TransferValidation.ValidateRequest(transferRequest);
        
        //transfer logic should happen here
        //it tells the server to reach db and use ig a change balance method to change
        //balance by transfer amount on the two accounts
        //public void changeBalance(int accNumber, int amount) and then amount could be negative
        dataBase.MakeTransfer(transferRequest);   
        //also some shit should be called that fills the TransferResultDto and sends it to frontend

        return null;

    }
    
    
}