using Database;
using Domain.DTOs;
using Grpc.Net.Client;

namespace Grpc;

public class ProtoClient:IGrpcClient
{
    public static async Task Main(string[] args) {}
    
    public async Task MakeTransfer(TransferRequestDTO transferRequestDto)
    {
        string serverAddress = "10.154.212.94:9090";
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
    
}