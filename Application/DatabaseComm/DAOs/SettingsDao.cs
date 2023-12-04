using Application.DaoInterfaces;
using Domain.DTOs;

namespace Grpc.DAOs;

public class SettingsDao : ISettingsDAO
{
    private readonly IGrpcClient grpcClient;
    
    public SettingsDao(IGrpcClient grpcClient)
    {
        this.grpcClient = grpcClient;
    }
    
    public async Task ChangeBaseRate(AccountNewBaseRateDTO accountNewBaseRateDto)
    {
        await grpcClient.ChangeBaseRate(accountNewBaseRateDto);
    }

    public async Task ChangeUserDetails(UserNewDetailsRequestDTO userNewDetailsRequestDto)
    {
        await grpcClient.ChangeUserDetails(userNewDetailsRequestDto);
    }
}