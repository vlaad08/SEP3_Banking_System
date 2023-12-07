using Database;
using Domain.DTOs;
using Domain.Models;
using Grpc.DAOs;
using Shared.DTOs;
using AccountsInfo = Domain.Models.AccountsInfo;
using Issue = Domain.Models.Issue;

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
   Task<DateTime?> CheckInterest(InterestCheckDTO loanRequestDto);
   Task<bool> CreditInterest(InterestCheckDTO loanRequestDto);
   Task RequestLoan(LoanRequestDTO dto);
   Task<IEnumerable<Transaction>> GetTransactions(GetTransactionsDTO getTransactionsDto);

   Task<string> GetUserByEmail(UserEmailDTO userEmailDto);
   Task RegisterUser(UserRegisterDto userRegisterDto);


   Task<int> getUserID(UserEmailDTO userEmailDto);
   Task CreateUserAccountNumber(AccountCreateRequestDto accountCreateRequestDto);
   Task UpdateBaseRate(AccountNewBaseRateDTO accountNewBaseRateDto);
   Task ChangeUserDetails(UserNewDetailsRequestDTO userNewDetailsRequestDto);
   Task SendMessage(SendMessageDTO sendMessageDto);
   Task<IEnumerable<Message>> GetMessagesForIssue(GetMessagesDTO dto);
   Task CreateIssue(IssueCreationDTO dto);
   Task<IEnumerable<Issue>> GetIssues();
   
   //

   Task UpdateEmail(UserNewEmailDTO userNewEmailDto);
   Task UpdatePassword(UserNewPasswordDTO userNewPasswordDto);
   Task UpdatePlan(UserNewPlanDTO userNewPlanDto);

   Task<IEnumerable<Transaction>> GetSubscriptions(GetTransactionsDTO getTransactionsDto);
}