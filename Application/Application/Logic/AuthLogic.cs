using System.ComponentModel.DataAnnotations;
using Application.DaoInterfaces;
using Shared.DTOs;
using Shared.Models;

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
        foreach (var user in users)
        {
            Console.WriteLine(user.Email);
        }
        User? existingUser = users.FirstOrDefault(u =>
            u.Email.Equals(userLoginRequestDto.Email, StringComparison.OrdinalIgnoreCase) &&
            u.Password.Equals(userLoginRequestDto.Password)
        );
        Console.WriteLine($"{existingUser.Email} {existingUser.FirstName} {existingUser.LastName}");
        
        return existingUser; //null if failed
    }
    public async Task<User> Login(UserLoginRequestDto userLoginRequestDto)
    {
        if (await ValidateUser(userLoginRequestDto) != null)
        {
            return await ValidateUser(userLoginRequestDto);
        }
        else
        {
            Console.WriteLine("Wrong username or password");
            return null;
        }
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