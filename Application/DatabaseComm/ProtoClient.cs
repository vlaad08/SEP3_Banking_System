using System.Collections;
using Database;
using Domain.DTOs;
using Domain.Models;
using Google.Protobuf.WellKnownTypes;
using Grpc.Net.Client;
using Shared.DTOs;
using AccountsInfo = Domain.Models.AccountsInfo;

namespace Grpc;

public class ProtoClient:IGrpcClient
{
    public static async Task Main(string[] args) {}
    private string serverAddress = "10.154.206.5:9090";

    public async Task MakeTransfer(TransferRequestDTO transferRequestDto)
    {
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

    public async Task<double> GetBalanceByAccountNumber(TransferRequestDTO transferRequestDto)
    {
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);
        
        var request = new BalanceCheckRequest()
        {
            AccountId = transferRequestDto.SenderAccountNumber
        };

        var response = await databaseClient.CheckBalanceAsync(request);
        return response.Balance;
    }

    public async Task<string> GetAccountNumberByAccountNumber(TransferRequestDTO transferRequestDto)
    {
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);
        
        var request = new AccountCheckRequest()
        {
            RecipientAccountId = transferRequestDto.RecipientAccountNumber
        };

        var response = await databaseClient.CheckAccountAsync(request);
        return response.RecipientAccountId;
    }

    public async Task<double> DailyCheck(TransferRequestDTO transferRequestDto)
    {
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);

        var request = new DailyCheckRequest()
        {
            AccountId = transferRequestDto.SenderAccountNumber
        };
        var response = await databaseClient.DailyCheckTransactionsAsync(request);
        return response.Amount;
    }

    public async Task MakeDeposit(DepositRequestDTO depositRequestDto)
    {
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);

        var request = new DepositRequest()
        {
            AccountId = depositRequestDto.ToppedUpAccountNumber,
            Amount = depositRequestDto.Amount
            
        };
        var response = await databaseClient.DepositAsync(request);
    }

    public async Task<List<global::Domain.Models.User>> GetAllUserInfo()
    {
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
                Id = responseUser.Id,
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
    public async Task<List<global::Domain.Models.AccountsInfo>> GetAllAccountsInfo()
    {
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);

        var request = new AllAccountsInfoRequest()
        {
        };

        var response = await databaseClient.AllAccountsInfoAsync(request);
        List<AccountsInfo> accountsInfos = new List<AccountsInfo>();
        foreach (var responseInfo in response.AccountInfo)
        {
            AccountsInfo accountInfo = new AccountsInfo
            {
                AccountNumber = responseInfo.AccountNumber,
                AccountOwner = responseInfo.OwnerName,
                Balance = responseInfo.AccountBalance,
                AccountType = responseInfo.AccountType
            };
            accountsInfos.Add(accountInfo);
        }
        return accountsInfos;
    }

    public async Task<List<AccountsInfo>> GetUserAccounts(UserLoginRequestDto userLoginRequestDto)
    {
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);
        var request = new UserAccountInfoRequest()
        {
            Email = userLoginRequestDto.Email
        };
        var response = await databaseClient.UserAccountsInfoAsync(request);
        List<AccountsInfo> accountsInfos = new List<AccountsInfo>();
        foreach (var resp in response.AccountInfo)
        {
            AccountsInfo accountInfo = new AccountsInfo()
            {
                AccountNumber = resp.AccountNumber,
                AccountOwner = resp.OwnerName,
                Balance = resp.AccountBalance,
                AccountType = resp.AccountType
            };
            accountsInfos.Add(accountInfo);
        }
        return accountsInfos;
    }

    public async Task<DateTime?> CheckInterest(InterestCheckDTO dto)
    {
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);
        var request = new LastInterestRequest()
        {
            AccoutNumber = dto.AccountID
        };
        var response = await databaseClient.LastInterestAsync(request);
        Timestamp timestamp= response?.Date;
        return timestamp?.ToDateTime();

    }

    public async Task<bool> CreditInterest(InterestCheckDTO dto)
    {
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);
        var request = new CreditInterestRequest()
        {
            AccountNumber = dto.AccountID
        };
        var response = await databaseClient.CreditInterestAsync(request);
        return response.Happened;
    }

    public async Task RequestLoan(LoanRequestDTO dto)
    {
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);
        DateTime utcEndDate = dto.EndDate.ToUniversalTime();
        var request = new LogLoanRequest
        {
            AccountId = dto.AccountNumber,
            RemainingAmount = dto.RemainingAmount,
            InterestRate = dto.InterestRate,
            MonthlyPayment = dto.MonthlyPayment,
            EndDate = Timestamp.FromDateTime(utcEndDate),
            LoanAmount = dto.Amount
        };
        var response = await databaseClient.LogLoanAsync(request);
    }
    
    public async Task<IEnumerable<Transaction>> GetTransactions(GetTransactionsDTO getTransactionsDto)
    {
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);
        var request = new GetTransactionsRequest
        {
            Email = getTransactionsDto.Email
        };
        var response = await databaseClient.GetTransactionsAsync(request);
        List<Transaction> transactions = new List<Transaction>();
        foreach (var t in response.Transactions)
        {
            Transaction transaction = new Transaction
            {
                SenderName = t.SenderName,
                RecipientName = t.ReceiverName,
                SenderAccountNumber = t.SenderAccountNumber,
                RecipientAccountNumber = t.RecipientAccountNumber,
                Amount = t.Amount,
                Message = t.Message,
                Date = t.Date.ToDateTime()
            };
            transactions.Add(transaction);
        }
        return transactions;
    }

    public Task SendMessage(SendMessageDTO sendMessageDto)
    {
        throw new NotImplementedException();
    }

    public Task<IEnumerable<Message>> GetMessagesForIssue(IssueGetterDTO dto)
    {
        throw new NotImplementedException();
    }

    public async Task CreateIssue(IssueCreationDTO dto)
    {
        using var channel = GrpcChannel.ForAddress($"http://{serverAddress}");
        var databaseClient = new DatabaseService.DatabaseServiceClient(channel);
        var request = new CreateIssueRequest
        {
            Title = dto.Title,
            Body = dto.Body,
            Owner = dto.Owner
        };
        var response = await databaseClient.CreateIssueAsync(request);
    }
}