using Application.DaoInterfaces;
using Domain.DTOs;

namespace DataAccess.DBComm;

public class DataBaseAcces : IDataBaseAccess
{
    public TransferRequestDTO request;
    public TransferResultDTO result;

    public DataBaseAcces(TransferRequestDTO request, TransferResultDTO result)
    {
        this.request = request;
        this.result = result;
    }

    public TransferResultDTO MakeTransfer(TransferRequestDTO request)
    {
        //async with await for response and then also deserialize data from DB
        
        return null;
    }
}