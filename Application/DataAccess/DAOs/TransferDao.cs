using Domain.DTOs;
using Application.DaoInterfaces;
using Domain.Models;

namespace DataAccess.DAOs;

public class TransferDAO : ITransferDAO
{
    //private readonly FileContext _fileContext;
    private readonly FileContext _fileContext;
    public TransferResultDTO TransferMoney(TransferRequestDTO transferRequest)
    {
        //HTTP request to the java DB
        //or no success 
        var result = new TransferResultDTO { Success = true, Message = "Money transferred successfully." };

        return result;
    }

    public string GetRecipientInfo( int accountNumber)
    {
        //reach db get the name based on accountnumber w/DBserver
        string info = "";
        return info;
    }

    public string GetSenderInfo(int accountNumber)
    {
        //reach db get senders info based on accountnumber
        //probably should do some json deseriliaze magic or parseint
        string info = "";
        return info;
    }

    public Task<TransferInfo> CreateAsync(TransferInfo transferInfo)
    {
        int id = 1;
        if (_fileContext.Transfers.Any())
        {
            id = _fileContext.Transfers.Max(t => t.Id);
            id++;
        }
        transferInfo.Id = id;
        _fileContext.Transfers.Add(transferInfo);
        _fileContext.SaveChanges();
        return Task.FromResult(transferInfo);
    }

    public async Task<IEnumerable<TransferInfo>> GetAsync()
    {
        IEnumerable<TransferInfo> transfers = _fileContext.Transfers.AsEnumerable();
        return transfers;
    }
    
}