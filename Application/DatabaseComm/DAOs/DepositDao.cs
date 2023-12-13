using Application.DaoInterfaces;
using Domain.DTOs;
using Grpc;

namespace DataAccess.DAOs;

public class DepositDao : IDepositDAO
{
    private readonly IGrpcClient grpcClient;
    public DepositDao(IGrpcClient grpcClient)
    {
        this.grpcClient = grpcClient;
    }

    public async Task DepositMoney(UpdatedDepositDTO updatedDepositDto)
    {
        await grpcClient.MakeDeposit(updatedDepositDto);    
    }
    
    public async Task<double> GetBalanceByAccountNumber(DepositRequestDTO dto)
    {
        GetBalanceDTO getBalanceDto = new GetBalanceDTO()
        {
            AccountId = dto.ToppedUpAccountNumber
        };
        double balance = await grpcClient.GetBalanceByAccountNumber(getBalanceDto);
        return balance;
    }
}