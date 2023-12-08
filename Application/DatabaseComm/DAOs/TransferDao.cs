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

    public async Task<UpdatedBalancesForTransferDTO> GiveBackNewBalance(TransferRequestDTO transferRequestDto)
    {
        var currentBalanceOfSender = await grpcClient.GetBalanceByAccountNumber(transferRequestDto);
        TransferRequestDTO temp = new TransferRequestDTO()
        {
            Amount = transferRequestDto.Amount,
            Message = transferRequestDto.Message,
            RecipientAccountNumber = transferRequestDto.SenderAccountNumber,
            SenderAccountNumber = transferRequestDto.RecipientAccountNumber
        };
        var currentBalanceOfReceiver = await grpcClient.GetBalanceByAccountNumber(temp);

        double newSenderBalance = currentBalanceOfSender - transferRequestDto.Amount;
        double newReceiverBalance = currentBalanceOfReceiver + transferRequestDto.Amount;

        UpdatedBalancesForTransferDTO dto = new UpdatedBalancesForTransferDTO()
        {
            newReceiverBalance = newReceiverBalance,
            newSenderBalance = newSenderBalance,
            Message = transferRequestDto.Message,
            senderId = transferRequestDto.SenderAccountNumber,
            receiverId = transferRequestDto.RecipientAccountNumber,
            amount = transferRequestDto.Amount
        };

        return dto;
    }

    public async Task TransferMoney(TransferRequestDTO transferRequestDto)
    {
        Console.WriteLine("DAO");
        Console.WriteLine("DAO TransferMoney");
        UpdatedBalancesForTransferDTO dto = await GiveBackNewBalance(transferRequestDto);
        await grpcClient.MakeTransfer(dto);
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