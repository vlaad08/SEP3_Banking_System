using Application.DaoInterfaces;
using Application.LogicInterfaces;
using Domain.DTOs;

namespace Application.Logic;

public class TransferLogic : ITransferLogic
{
    private readonly ITransferDAO _transferDAO;  

    public TransferLogic(ITransferDAO transferDAO)
    {
        _transferDAO = transferDAO;
    }

    public TransferResultDTO TransferMoney(TransferRequestDTO transferRequest)
    {
        //validation should look at DB see if acc number and name are matching
        
        //transfer logic should happen here
        
        
    }
    
    
}