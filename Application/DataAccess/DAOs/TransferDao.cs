using Domain.DTOs;
using Application.DaoInterfaces;
using Domain.Models;

namespace DataAccess.DAOs;

public class TransferDAO : ITransferDAO
{
    private IDataBaseAccess dataBaseAccess;
    public TransferDAO(IDataBaseAccess dataBaseAccess)
    {
        this.dataBaseAccess = dataBaseAccess;
    }

    public async Task TransferMoney(TransferRequestDTO transferRequest)
    {
        dataBaseAccess.MakeTransfer(transferRequest);
    }
    
    public double GetBalanceByAccountNumber(string accountNumber)
    {
        double balance = 0.0;
        return balance;
    }

    public string GetAccountNumberByAccountNumber(string accountNumber)
    {
        string accNum = "";
        return accNum;
    }
    
    
}