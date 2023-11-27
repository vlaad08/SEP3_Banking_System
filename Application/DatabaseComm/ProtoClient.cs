using System.Collections;
using Database;
using Domain.DTOs;
using Grpc.Net.Client;
using Shared.DTOs;

namespace Grpc;

public class ProtoClient:IGrpcClient
{
    public static async Task Main(string[] args) {}
    
    public async Task MakeTransfer(TransferRequestDTO transferRequestDto)
    {
        string serverAddress = "localhost:9090";
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
        string serverAddress = "localhost:9090";
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
        string serverAddress = "localhost:9090";
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);
        
        var request = new AccountCheckRequest()
        {
            RecipientAccountId = accountNumber
        };

        var response = await databaseClient.CheckAccountAsync(request);
        return response.RecipientAccountId;
    }

    public async Task<double> DailyCheck(string accountNumber)
    {
        string serverAddress = "localhost:9090";
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);

        var request = new DailyCheckRequest()
        {
            AccountId = accountNumber
        };
        var response = await databaseClient.DailyCheckTransactionsAsync(request);
        return response.Amount;
    }

    public async Task MakeDeposit(DepositRequestDTO depositRequestDto)
    {
        string serverAddress = "localhost:9090";
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);

        var request = new DepositRequest()
        {
            AccountId = depositRequestDto.ToppedUpAccountNumer,
            Amount = depositRequestDto.Amount
            
        };
        var response = await databaseClient.DepositAsync(request);
    }

    public async Task<List<global::Domain.Models.User>> GetAllUserInfo()
    {
        string serverAddress = "localhost:9090";
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);

        var request = new LoginValidationRequest()
        {
        };

        var response = await databaseClient.LoginValidationAsync(request);
        List<global::Domain.Models.User> users = new List<global::Domain.Models.User>();
        foreach (var responseUser in response.Users)
        {
            global::Domain.Models.User user = new global::Domain.Models.User()
            {
                Email = responseUser.Email,
                Password = responseUser.Password,
                FirstName = responseUser.FirstName,
                MiddleName = responseUser.MiddleName,
                LastName = responseUser.LastName,
                Role = responseUser.Role
            };
            users.Add(user);
        }
        return users;
    }
}