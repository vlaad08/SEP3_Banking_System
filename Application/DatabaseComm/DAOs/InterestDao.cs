using Application.DaoInterfaces;
using Domain.DTOs;

namespace Grpc.DAOs;

public class InterestDao : IInterestDAO
{
    private readonly IGrpcClient grpcClient;
    public InterestDao(IGrpcClient grpcClient)
    {
        this.grpcClient = grpcClient;
    }
    public async Task<bool> CreditInterest(InterestCheckDTO dto)
    {
        return await grpcClient.CreditInterest(dto);
    }

    public async Task<DateTime?> CheckInterest(InterestCheckDTO dto)
    {
        DateTime? dateTime = await grpcClient.CheckInterest(dto);
        return dateTime;
    }
}