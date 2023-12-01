using Domain.DTOs;
using Application.DaoInterfaces;
using Domain.Models;
using Grpc;

namespace DataAccess.DAOs;

public class TransferDAO : ITransferDAO
{
    private readonly IGrpcClient grpcClient;
    public TransferDAO(IGrpcClient grpcClient)
    {
        this.grpcClient = grpcClient;
    }

    public async Task TransferMoney(TransferRequestDTO transferRequestDto)
    {
        Console.WriteLine("DAO");
        Console.WriteLine("DAO TransferMoney");
        await  grpcClient.MakeTransfer(transferRequestDto);
    }

    public async Task<double> GetBalanceByAccountNumber(string accountNumber)
    {
        double balance = await grpcClient.GetBalanceByAccountNumber(accountNumber);
        Console.WriteLine(balance);
        
        return balance;
    }

    public async Task<string> GetAccountNumberByAccountNumber(string accountNumber)
    {
        string account = await grpcClient.GetAccountNumberByAccountNumber(accountNumber);
        return account;
        
    }

    public async Task<double> GetTransferAmountsByDayForUser(string accountNumber)
    {
        double amount = await grpcClient.DailyCheck(accountNumber);
        return amount;
    }
}