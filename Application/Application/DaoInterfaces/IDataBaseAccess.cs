using Domain.DTOs;

namespace Application.DaoInterfaces;

public interface IDataBaseAccess
{
    TransferResultDTO /*or gson*/ MakeTransfer(TransferRequestDTO transfer);
    
}