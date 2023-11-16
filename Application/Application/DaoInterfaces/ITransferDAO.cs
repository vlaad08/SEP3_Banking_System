using Domain.DTOs;
using Domain.Models;

namespace Application.DaoInterfaces;

public interface ITransferDAO
{
    Task TransferMoney(TransferRequestDTO transferRequest);
    Task<double> GetBalanceByAccountNumber(string accountNumber);
    Task<string> GetAccountNumberByAccountNumber(string accountNumber);
}