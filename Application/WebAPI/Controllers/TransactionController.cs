using Application.Logic;
using Application.LogicInterfaces;
using Domain.DTOs;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;

namespace Application.Controllers;

[ApiController]
[Route("api/[controller]")]
public class TransactionController : ControllerBase
{
    private readonly ITransferLogic transferLogic;
    private readonly IDepositLogic depositLogic;
    private readonly ILoanLogic loanLogic;

    public TransactionController(ITransferLogic transferLogic, IDepositLogic depositLogic,ILoanLogic loanLogic)
    {
        this.transferLogic = transferLogic;
        this.depositLogic = depositLogic;
        this.loanLogic = loanLogic;
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
            return Ok("Deposit successful");
        }
        catch (Exception e)
        {   
            return BadRequest(e.Message);;
        }
    }

    [HttpPost, Route("Loan/calculation")]
    public async Task<IActionResult> CalculateLoan([FromBody] LoanCalculationDTO dto)
    {
        try
        {
            double calculatedInterest = await loanLogic.CalculateLoan(dto);
            return Ok(calculatedInterest);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }

    [HttpPost, Route("Loan")]
    public async Task<IActionResult> RequestLoan([FromBody] LoanCalculationDTO dto)
    {
        try
        {
            await loanLogic.RequestLoan(dto);
            return Ok("Loan accepted!");
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
    
}