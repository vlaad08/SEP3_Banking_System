using Application.Logic;
using Application.LogicInterfaces;
using Domain.DTOs;
using Microsoft.AspNetCore.Mvc;

namespace Application.Controllers;

[ApiController]
[Route("api/[controller]")]
public class TransferController : ControllerBase
{
    private readonly ITransferLogic _transferLogic;
    private TransferValidation _transferValidation;

    public TransferController(ITransferLogic transferLogic)
    {
        _transferLogic = transferLogic;
    }

    [HttpPost,Route("Transfer")]
    public IActionResult TransferMoney([FromBody] TransferRequestDTO transferRequest)
    {
            //should validate somehow
            //we have the validation shit
        _transferValidation.ValidateRequest(transferRequest);
        
        var result = _transferLogic.TransferMoney(transferRequest);
        
       //should return ok or not ok which is the 
       return null;
    }
    
   
}