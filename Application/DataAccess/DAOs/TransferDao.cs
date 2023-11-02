using Domain.DTOs;
using Application.DaoInterfaces;
using Domain.Models;
using Grpc;

namespace DataAccess.DAOs;

public class TransferDAO : ITransferDAO
{
    //private readonly IDataBaseAccess dataBaseAccess;
    private readonly IGrpcClient _grpcClient;
    public TransferDAO(IGrpcClient grpcClient)
    {
        this._grpcClient = grpcClient;
    }

    public async Task TransferMoney(TransferRequestDTO transferRequestDto)
    {
       await  _grpcClient.MakeTransfer(transferRequestDto);
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