using Domain.DTOs;

namespace Application.DaoInterfaces;

public interface ITransferDAO
{
    TransferResultDTO TransferMoney(TransferRequestDTO transferRequest);
    string GetRecipientInfo(int accountNumber);
    string GetSenderInfo(int accountNumber);
}