using Application.DaoInterfaces;
using Domain.DTOs;

namespace Application.Logic;

public class TransferValidation
{
    private readonly ITransferDAO _transferDAO;

    public TransferValidation(ITransferDAO transferDAO)
    {
        _transferDAO = transferDAO;
    }

    public bool ValidateRequest(TransferRequestDTO transferRequest)
    {
        var recipientInfo = _transferDAO.GetRecipientInfo(transferRequest.RecipientAccountNumber);
        var senderInfo = _transferDAO.GetSenderInfo(transferRequest.SenderAccountNumber);
        
        return recipientInfo != null &&
               recipientInfo.RecipientName == transferRequest.RecipientName &&
               senderInfo.GetBalance >= transferRequest.Amount;
    }
}