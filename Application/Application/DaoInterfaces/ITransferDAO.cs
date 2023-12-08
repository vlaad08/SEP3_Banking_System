using Domain.DTOs;
using Domain.Models;

namespace Application.DaoInterfaces;

public interface ITransferDAO
{
    Task TransferMoney(TransferRequestDTO transferRequest);
    Task<double> GetBalanceByAccountNumber(TransferRequestDTO transferRequest);
    Task<string> GetAccountNumberByAccountNumber(TransferRequestDTO transferRequest);
    Task<double> GetTransferAmountsByDayForUser(TransferRequestDTO transferRequest);
    Task<IEnumerable<Transaction>> GetTransactions(GetTransactionsDTO getTransactionsDto);
    Task<IEnumerable<Transaction>> GetTransactions();
    Task FlagUser(FlagUserDTO flagUserDto);

    Task<IEnumerable<Transaction>> GetSubscriptions(GetTransactionsDTO getTransactionsDto);
    Task<UpdatedBalancesForTransferDTO> GiveBackNewBalance(TransferRequestDTO transferRequestDto);
}