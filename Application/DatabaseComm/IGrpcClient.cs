using Domain.DTOs;

namespace Grpc;

public interface IGrpcClient
{
   Task MakeTransfer(TransferRequestDTO transferRequestDto);
   Task<double> GetBalanceByAccountNumber(string accountNumber);
   Task<string> GetAccountNumberByAccountNumber(string accountNumber);
   Task<double> DailyCheck(string accountNumber);
   Task MakeDeposit(DepositRequestDTO depositRequestDto);
}