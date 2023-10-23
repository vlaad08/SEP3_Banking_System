using Application.DaoInterfaces;

namespace Application.LogicInterfaces;

public interface ITransferValidation
{
    //do i need this in here?
    bool ValidateRequest(ITransferDAO transferDAO);
    //i def need the asyncs ig
    
}   