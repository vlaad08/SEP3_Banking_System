namespace DataAccess.DBComm;
using Grpc.Net.Client;



public class protoClient
{
    public static void Main(string[] args)
    {
        var channel = GrpcChannel.ForAddress("geci");

        /*  var channel = GrpcChannel.ForAddress("your_grpc_server_address", new GrpcChannelOptions
            {
                Credentials = ChannelCredentials.Insecure
            });
        */


    }
}