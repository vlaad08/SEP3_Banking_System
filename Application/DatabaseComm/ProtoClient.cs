using Database;
using Domain.DTOs;
using Grpc.Net.Client;

namespace Grpc;

public class ProtoClient : IGrpcClient
{
    private DatabaseService.DatabaseServiceClient databaseClient = null;

    public ProtoClient()
    {
        Connect();
    }
    public async Task MakeTransfer(TransferRequestDTO transferRequestDto)
    {
        Connect();
        var transferRequest = new TransferRequest
        {
            SenderAccountId = transferRequestDto.SenderAccountNumber,
            RecipientAccountId = transferRequestDto.RecipientAccountNumber,
            Balance = transferRequestDto.Amount,
            Message = transferRequestDto.Message
        };

        var transferResponse = await databaseClient.TransferAsync(transferRequest);

        Console.WriteLine(transferResponse.Resp);
    }

    public void Connect()
    {
        string serverAddress = "10.154.212.94:9090";
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        databaseClient = new DatabaseService.DatabaseServiceClient(channel);
    }
}