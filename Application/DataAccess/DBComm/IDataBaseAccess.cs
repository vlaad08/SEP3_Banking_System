using Domain.DTOs;

namespace Application.DaoInterfaces;

public interface IDataBaseAccess
{
    Task MakeTransfer(TransferRequestDTO transfer);
    
}