using System.Collections;
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
        if (await transferDao.GetAccountNumberByAccountNumber(transferRequestDto) !=
            transferRequestDto.RecipientAccountNumber)
        {
            Console.WriteLine("Valid 1");
            throw new Exception("The account number does not exist!");
        }

        if (await transferDao.GetBalanceByAccountNumber(transferRequestDto) < transferRequestDto.Amount)
        {
            Console.WriteLine("Valid 2");
            throw new Exception("There is not sufficient balance to make the transaction!");
        }

        if ((await transferDao.GetTransferAmountsByDayForUser(transferRequestDto) + transferRequestDto.Amount) >=
            200000)
        {
            Console.WriteLine("Valid 3");
            throw new Exception("You have reached your daily limit!");
        }
        /*if (await transferDao.GetBalanceByAccountNumber(transferRequestDto)<transferRequestDto.Amount)
        {
            throw new Exception("Transaction amount exceeds Sender account balance!");
        }*/
    }

    public async Task TransferMoney(TransferRequestDTO transferRequest)
    {
        await ValidateTransfer(transferRequest);
        double oldSenderBalance = await transferDao.GetBalanceByAccountNumber(transferRequest);
        TransferRequestDTO temp = new TransferRequestDTO()
        {
            Amount = transferRequest.Amount,
            Message = transferRequest.Message,
            RecipientAccountNumber = transferRequest.SenderAccountNumber,
            SenderAccountNumber = transferRequest.RecipientAccountNumber
        };
        double oldRecipientBalance = await transferDao.GetBalanceByAccountNumber(transferRequest);
        double newSenderBalance = oldSenderBalance - transferRequest.Amount;
        double newRecipientBalance = oldRecipientBalance + transferRequest.Amount;
        UpdatedBalancesForTransferDTO dto = new UpdatedBalancesForTransferDTO()
        {
            newReceiverBalance = newRecipientBalance,
            newSenderBalance = newSenderBalance,
            Message = transferRequest.Message,
            senderId = transferRequest.SenderAccountNumber,
            receiverId = transferRequest.RecipientAccountNumber,
            amount = transferRequest.Amount
        };
        await transferDao.TransferMoney(dto);
    }

    public async Task<IEnumerable<Transaction>> GetTransactions(GetTransactionsDTO getTransactionsDto)
    {
        return await transferDao.GetTransactions(getTransactionsDto);
    }

    public async Task<IEnumerable<Transaction>> GetTransactions()
    {
        return await transferDao.GetTransactions();
    }

    public async Task FlagUser(FlagUserDTO dto)
    {
        await transferDao.FlagUser(dto);

    }

    public async Task<Dictionary<string, Subscription>> GetSubscriptions(GetTransactionsDTO getTransactionsDto)
    {
        IEnumerable<Transaction> listFromDataBase = await transferDao.GetSubscriptions(getTransactionsDto);

        List<Transaction> list = listFromDataBase.ToList();

        Dictionary<string, Subscription> dictionary = new Dictionary<string, Subscription>();


        foreach (var l in list)
        {
            if (!dictionary.ContainsKey(l.RecipientAccountNumber))
            {
                DateTime dateTime = l.Date.AddMonths(1);
                var subs = new Subscription()
                {
                    ServiceName = l.RecipientName,
                    Amount = l.Amount,
                    Date = dateTime
                };
                dictionary.TryAdd(l.RecipientAccountNumber, subs);
            }
        }



        return dictionary;
    }
}