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

    public TransferController(ITransferLogic transferLogic)
    {
        this.transferLogic = transferLogic;
    }

    [HttpPost,Route("Transfer")]
    public async void TransferMoney([FromBody] TransferRequestDTO transferRequest)
    {
        Console.WriteLine("transferRequest controller");
        await transferLogic.TransferMoney(transferRequest);
    }
    
   
}