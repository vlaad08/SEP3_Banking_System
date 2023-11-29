using Application.DaoInterfaces;
using Application.LogicInterfaces;
using Domain.Models;
using Shared.DTOs;

namespace WebAPI.Services;

public class AuthLogic : IAuthLogic
{
    private readonly IUserLoginDao userLoginDao;

    public AuthLogic(IUserLoginDao userLoginDao)
    {
        this.userLoginDao = userLoginDao;
    }
    private async Task<User> ValidateUser(UserLoginRequestDto userLoginRequestDto)
    {
        List<User> users = await userLoginDao.GetAllUserDataForValidation();


        User existingUser = null;
        foreach (var u in users)
        {
            
            
            if (u.Email!.Equals(userLoginRequestDto.Email) && u.Password!.Equals(userLoginRequestDto.Password))
            {
                existingUser = new User()
                {
                    Email = u.Email,
                    FirstName = u.FirstName,
                    LastName = u.LastName,
                    MiddleName = u.MiddleName,
                    Role = u.Role
                };
                break;
            }
        }

        return existingUser!; //null if failed
    }
    public async Task<User> Login(UserLoginRequestDto userLoginRequestDto)
    {
        return await ValidateUser(userLoginRequestDto);
    }

    public async Task<List<AccountsInfo>> GetAccounts()
    {
        return await userLoginDao.GetAccounts();
    }

    public async Task<List<AccountsInfo>> GetUserAccounts(string email)
    {
        return await userLoginDao.GetUserAccounts(email);
    }
   

    /*public Task<User> GetUser(string email, string password)
    {
        User? existingUser = users.FirstOrDefault(u => 
            u.Email.Equals(email, StringComparison.OrdinalIgnoreCase));

        if (existingUser == null)
        {
            throw new Exception("User not found");
        }

        return Task.FromResult(existingUser);
    }

    public Task RegisterUser(User user)
    {

        if (string.IsNullOrEmpty(user.Email))
        {
            throw new ValidationException("Username cannot be null");
        }

        if (string.IsNullOrEmpty(user.Password))
        {
            throw new ValidationException("Password cannot be null");
        }
        // Do more user info validation here
        
        // save to persistence instead of list
        
        users.Add(user);
        
        return Task.CompletedTask;
    }*/
    
    
    
    
    
    
}