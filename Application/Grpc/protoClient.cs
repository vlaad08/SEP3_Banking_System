using Database;
using Grpc.Net.Client;

namespace Grpc;


public class protoClient
{
    static async Task Main(string[] args)
    {
        using var channel = GrpcChannel.ForAddress("localhost");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);

        var transferRequest = new TransferRequest
        {
            SenderAccountId = "kaka",
            RecipientAccountId = "pisi",
            Balance = 69,
            Message = "kula"
        };

        var transferResponse = await databaseClient.TransferAsync(transferRequest);

        Console.WriteLine(transferResponse.Resp);
    }
}