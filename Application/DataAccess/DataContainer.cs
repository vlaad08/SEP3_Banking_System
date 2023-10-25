using Domain.Models;

namespace DataAccess;

public class DataContainer
{
    //do i need this w/ having a db?
    public ICollection<User> Users { get; set; }
    public ICollection<TransferInfo> Transfers { get; set; }
}