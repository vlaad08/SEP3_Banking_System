using Application.LogicInterfaces;
using Domain.DTOs;
using Microsoft.AspNetCore.Mvc;

namespace Application.Controllers;

[ApiController]
[Route("api/[controller]")]
public class TransferController : ControllerBase
{
    private readonly ITransferLogic _transferLogic;

    public TransferController(ITransferLogic transferLogic)
    {
        _transferLogic = transferLogic;
    }

    [HttpPost]
    public IActionResult TransferMoney([FromBody] TransferRequestDTO transferRequest)
    {
            //should validate somehow
        
        var result = _transferLogic.TransferMoney(transferRequest);
        
       //should return ok or not ok
    }
    
   
}