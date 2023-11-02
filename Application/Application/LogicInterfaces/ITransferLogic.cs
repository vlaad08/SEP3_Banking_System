using Domain.DTOs;
using Domain.Models;

namespace Application.LogicInterfaces;

public interface ITransferLogic
{
    Task TransferMoney(TransferRequestDTO transferRequest);
    
}