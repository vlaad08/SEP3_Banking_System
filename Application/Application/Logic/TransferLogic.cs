using Application.DaoInterfaces;
using Application.LogicInterfaces;
using Domain.DTOs;
using Domain.Models;
using iText.Kernel.Pdf;
using iText.Layout;
using iText.Layout.Element;

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
            throw new Exception("The account number does not exist!");
        }
        if (await transferDao.GetBalanceByAccountNumber(transferRequestDto) < transferRequestDto.Amount)
        {
            throw new Exception("There is not sufficient balance to make the transaction!");
        }
        if ((await transferDao.GetTransferAmountsByDayForUser(transferRequestDto) + transferRequestDto.Amount) >=
            200000)
        {
            throw new Exception("You have reached your daily limit!");
        }
        if (await transferDao.GetAccountNumberByAccountNumber(transferRequestDto) == transferRequestDto.SenderAccountNumber)
        {
            throw new Exception("Cannot transfer money to the same account!");
        }
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

    public async Task<byte[]> GenerateBankStatement(ExportRequestDTO exportRequestDto)
    {
        GetTransactionsDTO getTransactionsDto = new GetTransactionsDTO()
        {
            Email = exportRequestDto.Email
        };

        IEnumerable<Transaction> enumerable = await GetTransactions(getTransactionsDto);

        List<Transaction> list = new List<Transaction>();

        foreach (var transaction in enumerable)
        {
            if (transaction.Date > exportRequestDto.StartDate && transaction.Date < exportRequestDto.EndDate)
            {
                list.Add(transaction);
            }
        }

        using (var memoryStream = new MemoryStream())
        {
            using (var writer = new PdfWriter(memoryStream))
            {
                using (var pdf = new PdfDocument(writer))
                {
                    using (var document = new Document(pdf))
                    {
                        document.Add(new Paragraph("Bank Statement"));
                        document.Add(new Paragraph($"Statement Period: {exportRequestDto.StartDate.ToShortDateString()} to {exportRequestDto.EndDate.ToShortDateString()}"));
                        Table table = new Table(5);
                        table.AddCell("Date");
                        table.AddCell("Description");
                        table.AddCell("Amount (DKK)");
                        table.AddCell("Sender");
                        table.AddCell("Type");
                        foreach (var transaction in list)
                        {
                            table.AddCell(transaction.Date.ToShortDateString());
                            table.AddCell(transaction.Message);
                            table.AddCell(transaction.Amount.ToString());
                            table.AddCell(!transaction.transactionType.Equals("Deposit") ? transaction.SenderName : "");
                            table.AddCell(transaction.transactionType);
                        }
                        document.Add(table);
                    }
                }
            }
            return memoryStream.ToArray();
        }
    }

}