using System.ComponentModel;
using Application.DaoInterfaces;
using Database;
using Grpc;
using Shared.DTOs;
using User = Shared.Models.User;

namespace DataAccess.DAOs;

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