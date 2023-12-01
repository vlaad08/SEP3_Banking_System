using Database;
using Domain.DTOs;
using Shared.DTOs;
using AccountsInfo = Domain.Models.AccountsInfo;

namespace Grpc;

public interface IGrpcClient
{
   Task MakeTransfer(TransferRequestDTO transferRequestDto);
   Task<double> GetBalanceByAccountNumber(TransferRequestDTO transferRequestDto);
   Task<string> GetAccountNumberByAccountNumber(TransferRequestDTO transferRequestDto);
   Task<double> DailyCheck(TransferRequestDTO transferRequestDto);
   Task MakeDeposit(DepositRequestDTO depositRequestDto);
   Task<List<global::Domain.Models.User>> GetAllUserInfo();
   Task<List<AccountsInfo>> GetAllAccountsInfo();
   Task<List<AccountsInfo>> GetUserAccounts(UserLoginRequestDto userLoginRequestDto);

   Task<DateTime?> CheckInterest(InterestCheckDTO dto);
   Task<bool> CreditInterest(InterestCheckDTO dto);
}