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
        await grpcClient.MakeTransfer(transferRequestDto);
    }

    public async Task<double> GetBalanceByAccountNumber(TransferRequestDTO transferRequest)
    {
        double balance = await grpcClient.GetBalanceByAccountNumber(transferRequest);
        Console.WriteLine(balance);

        return balance;
    }

    public async Task<string> GetAccountNumberByAccountNumber(TransferRequestDTO transferRequest)
    {
        string account = await grpcClient.GetAccountNumberByAccountNumber(transferRequest);
        return account;

    }

    public async Task<double> GetTransferAmountsByDayForUser(TransferRequestDTO transferRequest)
    {
        double amount = await grpcClient.DailyCheck(transferRequest);
        return amount;
    }

    public async Task<IEnumerable<Transaction>> GetTransactions(GetTransactionsDTO getTransactionsDto)
    {
        return await grpcClient.GetTransactions(getTransactionsDto);
    }

    public async Task<IEnumerable<Transaction>> GetTransactions()
    {
        return await grpcClient.GetTransactions();
    }

    public async Task FlagUser(FlagUserDTO dto)
    {
        await grpcClient.FlagUser(dto);
    }

    public async Task<IEnumerable<Transaction>> GetSubscriptions(GetTransactionsDTO getTransactionsDto)
    {
        return await grpcClient.GetSubscriptions(getTransactionsDto);
    }
}