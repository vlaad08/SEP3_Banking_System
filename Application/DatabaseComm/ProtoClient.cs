using Database;
using Domain.DTOs;
using Grpc.Net.Client;

namespace Grpc;

public class ProtoClient:IGrpcClient
{
    public static async Task Main(string[] args) {}
    
    public async Task MakeTransfer(TransferRequestDTO transferRequestDto)
    {
        string serverAddress = "10.154.212.48:9090";
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);
        
        var transferRequest = new TransferRequest
        {
            SenderAccountId = transferRequestDto.SenderAccountNumber,
            RecipientAccountId = transferRequestDto.RecipientAccountNumber,
            Balance = transferRequestDto.Amount,
            Message = transferRequestDto.Message
        };
        var transferResponse = await databaseClient.TransferAsync(transferRequest);
    }

    public async Task<double> GetBalanceByAccountNumber(string accountNumber)
    {
        string serverAddress = "10.154.212.48:9090";
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);
        
        var request = new BalanceCheckRequest()
        {
            AccountId = accountNumber
        };

        var response = await databaseClient.CheckBalanceAsync(request);
        return response.Balance;
    }

    public async Task<string> GetAccountNumberByAccountNumber(string accountNumber)
    {
        string serverAddress = "10.154.212.48:9090";
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);
        
        var request = new AccountCheckRequest()
        {
            RecipientAccountId = accountNumber
        };

        var response = await databaseClient.CheckAccountAsync(request);
        return response.RecipientAccountId;
    }
}