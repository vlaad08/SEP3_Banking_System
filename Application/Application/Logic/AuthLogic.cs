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
            Console.WriteLine(u.Email + " " + u.Password);
            Console.WriteLine(userLoginRequestDto.Email + " " + userLoginRequestDto.Password);

            if (u.Email.Equals(userLoginRequestDto.Email))
            {
                Console.WriteLine("MUIE la dusmani");
            }

            if (u.Password.Equals(userLoginRequestDto.Password))
            {
                Console.WriteLine("TEST PASSWORD"); //DOES NOT PRINTS FSFSFSFSFSFF FUUCK MY FUCKING LIFE ITS 2 AM FUUUUUUUUUUUUUUUUUUUUUCK
                
                //in the database the password was a char value of 16 characters so it completed the rest of the space of the password with white space so it
                //didn't check. now it should work
            }

            Console.WriteLine(u.Password.Length);
            Console.WriteLine(userLoginRequestDto.Password.Length);
            
            if (u.Email!.Equals(userLoginRequestDto.Email) && u.Password!.Equals(userLoginRequestDto.Password))
            {
                existingUser = new User()
                {
                    Email = u.Email,
                    FirstName = u.FirstName,
                    LastName = u.LastName,
                    MiddleName = u.MiddleName,
                    Role = u.Role,
                    Money = 0 //For now leave it to 0
                };
                break;
            }
        }

        Console.WriteLine(existingUser.Email + " " + existingUser.FirstName + " " + existingUser.LastName);
        
        return existingUser!; //null if failed
    }
    public async Task<User> Login(UserLoginRequestDto userLoginRequestDto)
    {
       
        return await ValidateUser(userLoginRequestDto);
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