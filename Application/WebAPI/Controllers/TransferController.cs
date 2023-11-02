using Application.Logic;
using Application.LogicInterfaces;
using Domain.DTOs;
using Microsoft.AspNetCore.Mvc;

namespace Application.Controllers;

[ApiController]
[Route("api/[controller]")]
public class TransferController : ControllerBase
{
    private readonly ITransferLogic transferLogic;
    //private TransferValidation _transferValidation;

    public TransferController(ITransferLogic transferLogic)
    {
        this.transferLogic = transferLogic;
    }

    [HttpPost,Route("Transfer")]
    public /*async*/ void TransferMoney([FromBody] TransferRequestDTO transferRequest)
    {
            //should validate somehow
            //we have the validation shit
        //_transferValidation.ValidateRequest(transferRequest);
        Console.WriteLine("transferRequest");
        var result = /*await*/ transferLogic.TransferMoney(transferRequest);
    }
    
   
}