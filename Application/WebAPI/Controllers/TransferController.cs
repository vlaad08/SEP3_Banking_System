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

    [HttpPost, Route("Transfer")]
    public async Task<IActionResult> TransferMoney([FromBody] TransferRequestDTO transferRequest)
    {
        try
        {
            await transferLogic.TransferMoney(transferRequest);
            return Ok("Transfer successful");
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }

    [HttpPost, Route("Deposit")]
    public async Task<IActionResult> DepositMoney([FromBody] DepositRequestDTO depositRequest)
    {
        try
        {
            await transferLogic.DepositMoney(depositRequest);
            return Ok("Deposit succesful");
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);;
        }
    }
}