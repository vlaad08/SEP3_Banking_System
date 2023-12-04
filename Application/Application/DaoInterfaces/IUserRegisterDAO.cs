using Domain.DTOs;
using Grpc.DAOs;

namespace Application.DaoInterfaces;

public interface IUserRegisterDAO
{
    Task<string> VerifyUser(UserEmailDTO userEmailDto);
    Task RegisterUser(UserRegisterDto userRegisterDto);
    Task<int> GetUserId(UserEmailDTO userEmailDto);
    Task CreateUserAccountNumber(AccountCreateRequestDto accountCreateRequestDto);
    Task<string> VerifyAccountNumber(TransferRequestDTO transferRequestDto);
}