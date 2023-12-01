using System.Runtime.InteropServices.JavaScript;
using Application.DaoInterfaces;
using Application.Logic;
using Application.LogicInterfaces;
using Domain.DTOs;
using Domain.Models;
using Shared.DTOs;

namespace WebAPI.Services;

public class AuthLogic : IAuthLogic
{
    private readonly IUserLoginDao userLoginDao;
    private readonly IInterestDAO interestDao;
    public AuthLogic(IUserLoginDao userLoginDao, IInterestDAO interestDao)
    {
        this.userLoginDao = userLoginDao;
        this.interestDao = interestDao;
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
        return existingUser!;
    }
    public async Task<User> Login(UserLoginRequestDto userLoginRequestDto)
    {
        return await ValidateUser(userLoginRequestDto);
    }

    public async Task<List<AccountsInfo>> GetAccounts()
    {
        return await userLoginDao.GetAccounts();
    }

    public async Task<List<AccountsInfo>> GetUserAccounts(UserLoginRequestDto userLoginRequestDto)
    {
        List<AccountsInfo> accounts = await userLoginDao.GetUserAccounts(userLoginRequestDto);

        foreach (var a in accounts)
        {
            InterestCheckDTO dto = new InterestCheckDTO { AccountID = a.AccountNumber };
            DateTime? interestTimestamp = await interestDao.CheckInterest(dto);
            DateTime datePart;
            DateTime today;
            if (interestTimestamp != null)
            {
                datePart = interestTimestamp.Value.Date;
                today = DateTime.Now.Date;
                if ((interestTimestamp != null && !datePart.Equals(today) && DateTime.Now.Day == 1) || (interestTimestamp==null&&DateTime.Now.Day == 1))
                {
                    await interestDao.CreditInterest(dto);
                }
            }
        }
        return accounts;
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