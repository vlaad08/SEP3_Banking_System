using Application.DaoInterfaces;
using Application.LogicInterfaces;

namespace DataAccess.DAOs;

public class ValidateDao : ITransferValidationDAO
{
    private readonly FileContext _fileContext;

    //again this will prolly change just here for funnaroonies for now
    public ValidateDao(FileContext fileContext)
    {
        _fileContext = fileContext;
    }
    
    //need create async meth, validate rq shall become asyncronoush
}