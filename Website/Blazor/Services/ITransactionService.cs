namespace Blazor.Services.Http;

public interface ITransactionService
{
    public Task transfer(String senderAccount_id, String recipientAccount_id, double amount, String message);
}