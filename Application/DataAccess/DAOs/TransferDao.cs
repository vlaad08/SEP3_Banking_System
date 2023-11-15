using Domain.DTOs;
using Application.DaoInterfaces;
using Domain.Models;

namespace DataAccess.DAOs;

public class TransferDAO : ITransferDAO
{
    private IDataBaseAccess dataBaseAccess;
    public TransferDAO(IDataBaseAccess dataBaseAccess)
    {
        this.dataBaseAccess = dataBaseAccess;
    }

    public async Task TransferMoney(TransferRequestDTO transferRequest)
    {
        dataBaseAccess.MakeTransfer(transferRequest);
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
    
}