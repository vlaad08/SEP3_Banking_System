using Application.Logic;
using Application.LogicInterfaces;
using Domain.DTOs;
using Domain.Models;
using Microsoft.AspNetCore.Mvc;

namespace Application.Controllers;

[ApiController]
[Route("api/[controller]")]
public class TransactionController : ControllerBase
{
    private readonly ITransferLogic transferLogic;
    private readonly IDepositLogic depositLogic;
    private readonly ILoanLogic loanLogic;

    public TransactionController(ITransferLogic transferLogic, IDepositLogic depositLogic, ILoanLogic loanLogic)
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
            return BadRequest(e.Message);
        }
    }

    [HttpGet, Route("{Email}")]
    public async Task<ActionResult<IEnumerable<Transaction>>> GetTransactions([FromRoute] string Email)
    {
        try
        {
            GetTransactionsDTO dto = new GetTransactionsDTO
            {
                Email = Email
            };
            var transactions = await transferLogic.GetTransactions(dto);
            return Ok(transactions);
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
            return BadRequest();
        }
    }

    [HttpGet, Route("")]
    public async Task<ActionResult<IEnumerable<Transaction>>> GetTransactions()
    {
        try
        {
            var transactions = await transferLogic.GetTransactions();
            return Ok(transactions);
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
            return BadRequest();
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
            return BadRequest(e.Message);
            ;
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
            Console.WriteLine(e.Message);
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

    [HttpPatch, Route("Flag")]
    public async Task<IActionResult> FlagUser([FromBody] FlagUserDTO flagUserDto)
    {
        try
        {
            await transferLogic.FlagUser(flagUserDto);
            return Ok("User Flagged");
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }

    [HttpGet, Route("Subscriptions/{Email}")]
    public async Task<IActionResult> GetSubscriptions([FromRoute] string Email)
    {
        try
        {
            GetTransactionsDTO dto = new GetTransactionsDTO
            {
                Email = Email
            };
            var subscriptions = await transferLogic.GetSubscriptions(dto);
            return Ok(subscriptions);
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
            return BadRequest();
        }
    }
}