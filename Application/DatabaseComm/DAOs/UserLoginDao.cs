using Application.DaoInterfaces;
using Shared.DTOs;
using User = Domain.Models.User;

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
        Console.WriteLine("LoginDTO");
        await grpcClient.GetAllUserInfo();
    }

    public async Task<List<User>> GetAllUserDataForValidation()
    {
        return await grpcClient.GetAllUserInfo();
    }
}