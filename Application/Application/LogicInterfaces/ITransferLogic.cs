using Domain.DTOs;

namespace Application.LogicInterfaces;

public interface ITransferLogic
{
    TransferResultDTO TransferMoney(TransferRequestDTO transferRequest);
}