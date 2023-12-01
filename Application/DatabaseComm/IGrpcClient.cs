using Database;
using Domain.DTOs;
using Domain.Models;
using Shared.DTOs;
using AccountsInfo = Domain.Models.AccountsInfo;

namespace Grpc;

public interface IGrpcClient
{
   Task MakeTransfer(TransferRequestDTO transferRequestDto);
   Task<double> GetBalanceByAccountNumber(string accountNumber);
   Task<string> GetAccountNumberByAccountNumber(string accountNumber);
   Task<double> DailyCheck(string accountNumber);
   Task MakeDeposit(DepositRequestDTO depositRequestDto);
   Task<List<global::Domain.Models.User>> GetAllUserInfo();
   Task<List<AccountsInfo>> GetAllAccountsInfo();
   Task<List<AccountsInfo>> GetUserAccounts(string email);

   Task<DateTime?> CheckInterest(string account_id);
   Task<bool> CreditInterest(string account_id);
<<<<<<< Updated upstream
=======
   Task RequestLoan(LoanRequestDTO dto);
   Task<IEnumerable<Transaction>> GetTransactions(string email);
>>>>>>> Stashed changes
}