using Domain.DTOs;

namespace DataAccess.DAOs;

public class TransferDAO : ITransferDAO
{
    
    public TransferResultDTO TransferMoney(TransferRequestDTO transferRequest)
    {
        //HTTP request to the java DB

        
        var result = new TransferResultDTO { Success = true, Message = "Money transferred successfully." };

        return result;
    }

    public string GetRecipientInfo( int accountNumber)
    {
        //reach db get the name with accountnumber
    }
}