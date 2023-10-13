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

    public bool ValidateRecipient(TransferRequestDTO transferRequest)
    {
        // Placeholder for validation logic
        // Query the Java server's REST API to get recipient information based on the account number
        var recipientInfo = _transferDAO.GetRecipientInfo(transferRequest.RecipientAccountNumber);

        // Check if the retrieved information matches the request
        return recipientInfo != null &&
               recipientInfo.RecipientName == transferRequest.RecipientName;
    }
}