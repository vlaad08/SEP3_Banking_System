using System.Security.Claims;
using System.Text;
using System.Text.Json;
using Shared.DTOs;

namespace Blazor.Services.Http;

public class TransactionService : ITransactionService
{   
    private readonly HttpClient client = new ();
    public async Task transfer(String senderAccount_id, String recipientAccount_id, double amount, String message)
    {
        TransferDto transfer = new TransferDto()
        {
            SenderAccountNumber = senderAccount_id,
            RecipientAccountNumber = recipientAccount_id,
            Amount = amount,
            Message = message,
        };

        string transferJson = JsonSerializer.Serialize(transfer);
        Console.WriteLine(transferJson);
        StringContent content = new(transferJson, Encoding.UTF8, "application/json");
        HttpResponseMessage response = await client.PostAsync("http://localhost:5054/api/Transfer/Transfer", content);
        string m = await response.Content.ReadAsStringAsync();
        if (!response.IsSuccessStatusCode)
        {
            throw new Exception(m);
        }
    }
}