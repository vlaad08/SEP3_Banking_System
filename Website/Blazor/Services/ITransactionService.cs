using Domain.DTOs;
using Shared.DTOs;
using Shared.Models;
using Domain.Models;
using Microsoft.AspNetCore.Components;

namespace Blazor.Services.Http;

public interface ITransactionService
{
    public Task Transfer(String senderAccount_id, String recipientAccount_id, double amount, String message);

    public Task Deposit(string accountNumber, double amount);

    public Task<List<Transaction>> GetTransactions(string email);

    public Task<IEnumerable<Transaction>> GetTransactions();

    public Task FlagUser(FlagUserDto flagUserDto);
    public Task<Dictionary<string, SubscriptionDao>> GetSubscriptions(string email);
    public Task ExportBankStatement(ExportRequestDTO exportRequestDto);
}