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
    private readonly IDepositLogic depositLogic;

    public TransferController(ITransferLogic transferLogic, IDepositLogic depositLogic)
    {
        this.transferLogic = transferLogic;
        this.depositLogic = depositLogic;
    }

    [HttpPost, Route("Transfer")]
    public async Task<IActionResult> TransferMoney([FromBody] TransferRequestDTO transferRequest)
    {
        try
        {
            Console.WriteLine("Controller");
            await transferLogic.TransferMoney(transferRequest);
            return Ok("Transfer successful");
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
            return BadRequest(e.Message);
        }
    }

    [HttpPost, Route("Deposit")]
    public async Task<IActionResult> DepositMoney([FromBody] DepositRequestDTO depositRequest)
    {
        try
        {
            await depositLogic.DepositMoney(depositRequest);
            return Ok("Deposit succesful");
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);;
        }
    }
}