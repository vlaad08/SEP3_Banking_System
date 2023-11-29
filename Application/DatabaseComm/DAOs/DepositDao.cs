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

    public async Task DepositMoney(DepositRequestDTO depositRequestDto)
    {
        await grpcClient.MakeDeposit(depositRequestDto);    
    }
}