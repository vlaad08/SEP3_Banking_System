using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using System.Text.Json;
using Application.LogicInterfaces;
using Domain.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using Shared.DTOs;
using WebAPI.Services;
using AccountsInfo = Database.AccountsInfo;


namespace WebApi.Controllers;

[ApiController]
[Route("[controller]")]
public class AuthController : ControllerBase
{
    private readonly IConfiguration config;
    private readonly IAuthLogic _authLogic;

    public AuthController(IConfiguration config, IAuthLogic authLogic)
    {
        this.config = config;
        this._authLogic = authLogic;
    }

 /*[HttpPost, Route("register")]
 public async Task<ActionResult> Register([FromBody] User user)
 {
     await _authLogic.RegisterUser(user);
     return Ok();
 }*/

 [HttpPost, Route("login")]
 public async Task<ActionResult> Login([FromBody] UserLoginRequestDto userLoginRequestDto)
 {
     try
     {
         User user = await _authLogic.Login(userLoginRequestDto);
         List<Domain.Models.AccountsInfo> accountsInfos = await _authLogic.GetUserAccounts(user.Email);
         user.AccountsInfos = accountsInfos;    
         string token = GenerateJwt(user);
         return Ok(token);
     }
     catch (Exception e)
     {
         Console.WriteLine(e.StackTrace);
         return BadRequest(e.Message);
     }
 }

 private string GenerateJwt(User user)
 {
     List<Claim> claims = GenerateClaims(user);

     SymmetricSecurityKey key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(config["Jwt:Key"]));
     SigningCredentials signIn = new SigningCredentials(key, SecurityAlgorithms.HmacSha512);

     JwtHeader header = new JwtHeader(signIn);

     JwtPayload payload = new JwtPayload(
         config["Jwt:Issuer"],
         config["Jwt:Audience"],
         claims,
         null,
         DateTime.UtcNow.AddMinutes(60));

     JwtSecurityToken token = new JwtSecurityToken(header, payload);

     string serializedToken = new JwtSecurityTokenHandler().WriteToken(token);
     return serializedToken;
 }

 private List<Claim> GenerateClaims(User user)
 {
     try
     {

         string fullName = $"{user.FirstName} {user.MiddleName} {user.LastName}";

         string list = JsonSerializer.Serialize(user.AccountsInfos);
         Console.WriteLine(list);
         Console.WriteLine(user.Role);
         
         var claims = new[]
         {
             new Claim(JwtRegisteredClaimNames.Sub, config["Jwt:Subject"]),
             new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
             new Claim(JwtRegisteredClaimNames.Iat, DateTime.UtcNow.ToString()),
             new Claim(ClaimTypes.Role, user.Role),
             new Claim(ClaimTypes.Name, fullName),
             new Claim("Accounts", list)
         };
         
         return claims.ToList();
     }
     catch (Exception e)
     {
         Console.WriteLine(e);
         throw;
     }
 }
}