using Application.DaoInterfaces;
using Domain.DTOs;

namespace Grpc.DAOs;

public class UserRegisterDao : IUserRegisterDAO
{
    
    private readonly IGrpcClient grpcClient;

    public UserRegisterDao(IGrpcClient grpcClient)
    {
        this.grpcClient = grpcClient;
    }
    
    
    public async Task<string> VerifyUser(UserEmailDTO userEmailDto)
    {
        string emailToVerify = await grpcClient.GetUserByEmail(userEmailDto);
        return emailToVerify;
    }

    public async Task RegisterUser(UserRegisterDto userRegisterDto)
    {
        await grpcClient.RegisterUser(userRegisterDto);
    }

    public async Task<int> GetUserId(UserEmailDTO userEmailDto)
    {
        return await grpcClient.getUserID(userEmailDto);
    }

    public async Task CreateUserAccountNumber(AccountCreateRequestDto accountCreateRequestDto)
    {
        await grpcClient.CreateUserAccountNumber(accountCreateRequestDto);
    }

    public async Task<string> VerifyAccountNumber(TransferRequestDTO transferRequestDto)
    {
        return await grpcClient.GetAccountNumberByAccountNumber(transferRequestDto);
    }
}