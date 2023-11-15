using Domain.DTOs;
using Domain.Models;

namespace Application.DaoInterfaces;

public interface ITransferDAO
{
    Task TransferMoney(TransferRequestDTO transferRequest);
    string GetRecipientInfo(int accountNumber);
    string GetSenderInfo(int accountNumber);
}