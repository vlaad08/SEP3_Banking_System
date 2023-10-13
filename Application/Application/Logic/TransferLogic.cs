using Application.DaoInterfaces;
using Application.LogicInterfaces;
using Domain.DTOs;

namespace Application.Logic;

public class TransferLogic : ITransferLogic
{
    private readonly ITransferDAO _transferDTO;  

    public TransferLogic(ITransferDAO transferDTO)
    {
        _transferDTO = transferDTO;
    }

    public TransferResultDTO TransferMoney(TransferRequestDTO transferRequest)
    {
        TransferValidation.ValidateRequest(transferRequest);
        
        //transfer logic should happen here

        return null;

    }
    
    
}