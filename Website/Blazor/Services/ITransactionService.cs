using Shared.DAO;

namespace Blazor.Services.Http;

public interface ITransactionService
{
    public Task Transfer(String senderAccount_id, String recipientAccount_id, double amount, String message);

    public Task Deposit(string accountNumber, double amount);

    public Task<List<TransactionDao>> GetTransactions(string email);
}