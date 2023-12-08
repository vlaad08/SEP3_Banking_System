using Domain.DTOs;
using Domain.Models;

namespace Application.LogicInterfaces;

public interface ITransferLogic
{
    Task TransferMoney(TransferRequestDTO transferRequest);
    Task<IEnumerable<Transaction>> GetTransactions(GetTransactionsDTO getTransactionsDto);
    Task<IEnumerable<Transaction>> GetTransactions();
    Task FlagUser(FlagUserDTO flagUserDto);
    Task<Dictionary<string, Subscription>> GetSubscriptions(GetTransactionsDTO getTransactionsDto);
}