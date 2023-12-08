using Application.DaoInterfaces;
using Application.LogicInterfaces;
using Domain.DTOs;
using Microsoft.AspNetCore.Mvc;

namespace WebAPI.Controllers;

[ApiController]
[Route("[controller]/update")]

public class SettingsController : ControllerBase
{
    private readonly ISettingsLogic settingsLogic;

    public SettingsController(ISettingsLogic settingsLogic)
    {
        this.settingsLogic = settingsLogic;
    }
    

    [HttpPost, Route("email")]
    public void UpdateEmail([FromBody] UserNewEmailDTO userNewEmailDto)
    {
        try
        {
            settingsLogic.UpdateEmail(userNewEmailDto);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    [HttpPost, Route("password")]
    public void UpdatePassword([FromBody] UserNewPasswordDTO userNewPasswordDto)
    {
        try
        {
            settingsLogic.UpdatePassword(userNewPasswordDto);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }

    [HttpPost, Route("plan")]
    public void UpdatePlan([FromBody] UserNewPlanDTO userNewPlanDto)
    {
        try
        {
            settingsLogic.UpdatePlan(userNewPlanDto);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            throw;
        }
    }
}