using Application.DaoInterfaces;
using Domain.DTOs;
using Domain.Models;

namespace Application.LogicInterfaces;

public interface ITransferValidation
{
    //do i need this in here?
    // fuck this bool ValidateRequest(ITransferDAO transferDAO);
    //i def need the asyncs ig
    
    public Task<TransferInfo> CreateAsync(TransferRequestDTO transferRequestDto);
    public Task<TransferInfo> GetAsync(TransferResultDTO transferResultDto);
}   