using Application.DaoInterfaces;
using Database;
using Shared.DTOs;
using User = Domain.Models.User;
using AccountsInfo = Domain.Models.AccountsInfo;

namespace Grpc.DAOs;

public class UserLoginDao : IUserLoginDao
{
    private readonly IGrpcClient grpcClient;
    public UserLoginDao(IGrpcClient grpcClient)
    {
        this.grpcClient = grpcClient;
    }

    public async Task GetAllUserDataForValidation(UserLoginRequestDto userLoginRequestDto)
    {
        await grpcClient.GetAllUserInfo();
    }

    public async Task<List<User>> GetAllUserDataForValidation()
    {
        return await grpcClient.GetAllUserInfo();
    }

    public async Task<List<AccountsInfo>> GetAccounts()
    {
        return await grpcClient.GetAllAccountsInfo();
    }

    public async Task<List<AccountsInfo>> GetUserAccounts(UserLoginRequestDto userLoginRequestDto)
    {
        return await grpcClient.GetUserAccounts(userLoginRequestDto);
    }
}