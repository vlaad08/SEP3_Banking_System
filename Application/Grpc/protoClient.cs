using Database;
using Grpc.Net.Client;

namespace Grpc;


public class protoClient
{
    static async Task Main(string[] args)
    {
        using var channel = GrpcChannel.ForAddress("http://localhost:9090");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);

        var transferRequest = new TransferRequest
        {
            SenderAccountId = "aaaabbbbccccdddd",
            RecipientAccountId = "aaaabbbbddddcccc",
            Balance = 200,
            Message = "-"
        };

        var transferResponse = await databaseClient.TransferAsync(transferRequest);

        Console.WriteLine(transferResponse.Resp);
    }
}