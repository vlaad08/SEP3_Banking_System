using System.Security.Claims;
using System.Text;
using Domain.Models;
using Newtonsoft.Json;
using Shared.DAO;
using Shared.DTOs;
using Shared.Models;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace Blazor.Services.Http;

public class TransactionService : ITransactionService
{   
    private readonly HttpClient client = new ();
    public async Task Transfer(String senderAccount_id, String recipientAccount_id, double amount, String message)
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
            StringContent content = new(transferJson, Encoding.UTF8, "application/json");
            Console.WriteLine(content);
            HttpResponseMessage response = await client.PostAsync("http://localhost:5054/api/Transaction/Transfer", content);
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

    public async Task Deposit(string toppedUpAccountNumber, double amount)
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
                await client.PostAsync("http://localhost:5054/api/Transaction/Deposit", content);
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

    public async Task<List<TransactionDao>> GetTransactions(string email)
    {
        try
        {
            HttpResponseMessage responseMessage = await client.GetAsync($"http://localhost:5054/api/Transaction/{email}");
            string responseBody = await responseMessage.Content.ReadAsStringAsync();
            
            if (!responseMessage.IsSuccessStatusCode)
            {
                throw new Exception(responseBody);
            }

            List<TransactionDao> list = JsonConvert.DeserializeObject<IEnumerable<TransactionDao>>(responseBody)
                .ToList();

            
            return list;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    public async Task<Dictionary<string, SubscriptionDao>> GetSubscriptions(string Email)
    {
        try
        {
            HttpResponseMessage responseMessage = await client.GetAsync($"http://localhost:5054/api/Transaction/Subscriptions/{Email}");
            string responseBody = await responseMessage.Content.ReadAsStringAsync();
            
            if (!responseMessage.IsSuccessStatusCode)
            {
                throw new Exception(responseBody);
            }
            Dictionary<string, SubscriptionDao> dictionary =
                JsonConvert.DeserializeObject<Dictionary<string, SubscriptionDao>>(responseBody);

            
            
            return dictionary;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
}