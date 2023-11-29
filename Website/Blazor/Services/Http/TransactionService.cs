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
        try
        {
            string transferJson = JsonSerializer.Serialize(transfer);
            Console.WriteLine(transferJson);
            StringContent content = new(transferJson, Encoding.UTF8, "application/json");
            HttpResponseMessage response = await client.PostAsync("http://localhost:5054/api/Transfer/Transfer", content);
            string responseBody = await response.Content.ReadAsStringAsync();
            if (!response.IsSuccessStatusCode)
            {
                throw new Exception(responseBody);
            }
            Console.WriteLine("Transfer successful");
        }
        catch (Exception e)
        { 
            throw new Exception($"Transfer failed: {e.Message}");
        }
    }

    public async Task deposit(string toppedUpAccountNumber, double amount)
    {
        DepositDto deposit = new DepositDto()
        {
            ToppedUpAccountNumber = toppedUpAccountNumber,
            Amount = amount
        };

        try
        {
            string depositJson = JsonSerializer.Serialize(deposit);
            StringContent content = new(depositJson, Encoding.UTF8, "application/json");
            HttpResponseMessage responseMessage =
                await client.PostAsync("http://localhost:5054/api/Transfer/Deposit", content);
            string responseBody = await responseMessage.Content.ReadAsStringAsync();
            if (!responseMessage.IsSuccessStatusCode)
            {
                throw new Exception(responseBody);
            }

            Console.WriteLine("Deposit works");
        }
        catch (Exception e)
        {
            throw new Exception($"Deposit failed: {e.Message}");
        }

    }
}