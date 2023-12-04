using Application.DaoInterfaces;
using Application.LogicInterfaces;
using Domain.DTOs;
using Microsoft.AspNetCore.Mvc;

namespace WebAPI.Controllers;

[ApiController]
[Route("[controller]")]

public class SettingsController : ControllerBase
{
    private readonly ISettingsLogic settingsLogic;

    public SettingsController(ISettingsLogic settingsLogic)
    {
        this.settingsLogic = settingsLogic;
    }

    [HttpPost, Route("updateUser")]
    public void UpdateUser([FromBody] UserNewDetailsRequestDTO userNewDetailsRequestDto)
    {
        try
        {
            settingsLogic.UpdateUser(userNewDetailsRequestDto);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
}