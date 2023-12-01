using System.Security.AccessControl;
using System.Threading.Channels;
using Application.DaoInterfaces;
using Application.LogicInterfaces;
using Domain.DTOs;
using Domain.Models;

namespace Application.Logic;

public class TransferLogic : ITransferLogic
{
    private readonly ITransferDAO transferDao;

    public TransferLogic(ITransferDAO transferDAO)
    {
        this.transferDao = transferDAO;
    }

    private async Task ValidateTransfer(TransferRequestDTO transferRequestDto)
    {
        if (transferRequestDto.SenderAccountNumber.Equals(null) ||
            transferRequestDto.RecipientAccountNumber.Equals(null) ||
            transferRequestDto.Amount < 0)
        {
            throw new Exception("Please fill out the fields correctly");
        }
        
        Console.WriteLine("Valid 1");
        if (await transferDao.GetAccountNumberByAccountNumber(transferRequestDto.RecipientAccountNumber) != transferRequestDto.RecipientAccountNumber)
        {
            Console.WriteLine("Valid 1");
            throw new Exception("The account number does not exist!");
        }

        if (await transferDao.GetBalanceByAccountNumber(transferRequestDto.SenderAccountNumber) < transferRequestDto.Amount)
        {
            Console.WriteLine("Valid 2");
            throw new Exception("There is not sufficient balance to make the transaction!");
        }

        if ((await transferDao.GetTransferAmountsByDayForUser(transferRequestDto.SenderAccountNumber)+transferRequestDto.Amount)>= 200000)
        {
            Console.WriteLine("Valid 3");
            throw new Exception("You have reached your daily limit!");
        }
    }   

    public async Task TransferMoney(TransferRequestDTO transferRequest)
    {
        Console.WriteLine("Logic 1");
        await ValidateTransfer(transferRequest);
        Console.WriteLine("Logic 2");
        await transferDao.TransferMoney(transferRequest);
        Console.WriteLine("Logic 3");
    }
    
    
    
}