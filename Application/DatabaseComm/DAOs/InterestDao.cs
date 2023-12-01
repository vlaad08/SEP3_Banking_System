using Application.DaoInterfaces;

namespace Grpc.DAOs;

public class InterestDao : IInterestDAO
{
    private readonly IGrpcClient grpcClient;
    public InterestDao(IGrpcClient grpcClient)
    {
        this.grpcClient = grpcClient;
    }
    public async Task<bool> CreditInterest(string account_id)
    {
        return await grpcClient.CreditInterest(account_id);
    }

    public async Task<DateTime?> CheckInterest(string account_id)
    {
        DateTime? dateTime = await grpcClient.CheckInterest(account_id);
        return dateTime;
    }
}