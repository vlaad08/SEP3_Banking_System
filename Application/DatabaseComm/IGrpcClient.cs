using Database;
using Domain.DTOs;
using Shared.DTOs;

namespace Grpc;

public interface IGrpcClient
{
    Task MakeTransfer(TransferRequestDTO transferRequestDto);
    Task<double> GetBalanceByAccountNumber(string accountNumber);
    Task<string> GetAccountNumberByAccountNumber(string accountNumber);
    Task<double> DailyCheck(string accountNumber);
    Task MakeDeposit(DepositRequestDTO depositRequestDto);
    Task<List<global::Shared.Models.User>> GetAllUserInfo();
    Task<List<AccountsInfo>> getAllAccountsInfo();
}
