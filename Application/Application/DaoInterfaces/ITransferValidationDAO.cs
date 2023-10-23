using Domain.Models;

namespace Application.DaoInterfaces;

public class ITransferValidationDAO
{
    public Task<TransferInfo> CreateAsync(TransferInfo transferInfo);
    public Task<IEnumerable<TransferInfo>> GetAsync();
}