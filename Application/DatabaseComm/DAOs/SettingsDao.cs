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
    
    public async Task UpdateBaseRate(AccountNewBaseRateDTO accountNewBaseRateDto)
    {
        await grpcClient.UpdateBaseRate(accountNewBaseRateDto);
    }

    public async Task ChangeUserDetails(UserNewDetailsRequestDTO userNewDetailsRequestDto)
    {
        await grpcClient.ChangeUserDetails(userNewDetailsRequestDto);
    }

    public async Task UpdateEmail(UserNewEmailDTO userNewEmailDto)
    {
        await grpcClient.UpdateEmail(userNewEmailDto);
    }

    public async Task UpdatePassword(UserNewPasswordDTO userNewPasswordDto)
    {
        await grpcClient.UpdatePassword(userNewPasswordDto);
    }

    public async Task UpdatePlan(UserNewPlanDTO userNewPlanDto)
    {
        await grpcClient.UpdatePlan(userNewPlanDto);
    }
}