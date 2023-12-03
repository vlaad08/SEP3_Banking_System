using Domain.DTOs;
using Domain.Models;
using Grpc.DAOs;
using Shared.DTOs;

namespace Application.LogicInterfaces;

public interface IAuthLogic
{
    Task<User> Login(UserLoginRequestDto userLoginRequestDto);
    Task<List<AccountsInfo>> GetAccounts();
    Task<List<AccountsInfo>> GetUserAccounts(UserLoginRequestDto userLoginRequestDto);

    Task<bool> VerifyUser(UserRegisterDto userRegisterDto);
    Task RegisterUser(UserRegisterDto userRegisterDto);

    Task<int> GetUserId(UserEmailDTO userEmailDto);
    Task<bool> VerifyAccountNumber(TransferRequestDTO transferRequestDto);
    Task CreateUserAccountNumber(AccountCreateRequestDto accountCreateRequestDto);
}