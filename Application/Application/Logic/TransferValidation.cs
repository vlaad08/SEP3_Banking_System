using Application.DaoInterfaces;
using Application.LogicInterfaces;
using Domain.DTOs;
using Domain.Models;

namespace Application.Logic;

public class TransferValidation : ITransferValidation //IM fucked in the head ive been using the class w/out any interfaces so DAO IDAO ILOGIC
{
    //ADD ASYNC!!!!
    private readonly ITransferDAO _transferDAO;

    public TransferValidation(ITransferDAO transferDAO)
    {
        _transferDAO = transferDAO;
    }

    //this monstorsity below is supposed to check if the transaction can happen
    //cuz when user types in ifno they can mess up so we check if users with those acc number exist and if
    //the senders balance is bigger than equal to the amount wish to be transferred
    public bool ValidateRequest(TransferRequestDTO transferRequest)
    {
        var recipientInfo = _transferDAO.GetRecipientInfo(transferRequest.RecipientAccountNumber);
        var senderInfo = _transferDAO.GetSenderInfo(transferRequest.SenderAccountNumber);
        
        return recipientInfo != null &&
               recipientInfo.RecipientName == transferRequest.RecipientName &&
               senderInfo.GetBalance >= transferRequest.Amount;
    }

    public async Task<TransferInfo> CreateAsync(TransferRequestDTO transferRequestDto)
    {
        
    }

    public async Task<TransferInfo> GetAsync()
    {
        
    }

}