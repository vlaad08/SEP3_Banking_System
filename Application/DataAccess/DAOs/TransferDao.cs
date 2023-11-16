using Domain.DTOs;
using Application.DaoInterfaces;
using Domain.Models;
using Grpc;

namespace DataAccess.DAOs;

public class TransferDAO : ITransferDAO
{
    private readonly IGrpcClient _grpcClient;
    public TransferDAO(IGrpcClient grpcClient)
    {
        this._grpcClient = grpcClient;
    }

    public async Task TransferMoney(TransferRequestDTO transferRequestDto)
    { 
        Console.WriteLine("DAO TransferMoney");
        await  _grpcClient.MakeTransfer(transferRequestDto);
    }

    public async Task<double> GetBalanceByAccountNumber(string accountNumber)
    {
        double balance = await _grpcClient.GetBalanceByAccountNumber(accountNumber);
        return balance;
    }

    public async Task<string> GetAccountNumberByAccountNumber(string accountNumber)
    {
        string account = await _grpcClient.GetAccountNumberByAccountNumber(accountNumber);
        return account;
        
    }
}