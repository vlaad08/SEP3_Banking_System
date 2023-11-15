using Domain.DTOs;
using Domain.Models;

namespace Application.DaoInterfaces;

public interface ITransferDAO
{
    Task TransferMoney(TransferRequestDTO transferRequest);
    double GetBalanceByAccountNumber(string accountNumber);
    string GetAccountNumberByAccountNumber(string accountNumber);

}