using Application.LogicInterfaces;
using Domain.DTOs;
using Domain.Models;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using Microsoft.VisualBasic;

namespace WebAPI.Controllers;

[ApiController]
[Route("[controller]")]
public class IssueController : ControllerBase
{
    private readonly IIssueLogic issueLogic;

    public IssueController(IIssueLogic issueLogic)
    {
        this.issueLogic = issueLogic;
    }

    [HttpPost, Route("Issue")]
    public async Task<ActionResult> CreateIssue([FromBody] IssueCreationDTO dto)
    {
        try
        {
            Console.WriteLine(dto.Title+" "+dto.Body+" "+dto.Owner);
            await issueLogic.CreateIssue(dto);
            return Ok("Message sent");
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
            throw new Exception(e.Message);
        }
    }
    
    [HttpGet, Route("Employee/Issues")]
    public async Task<ActionResult> GetIssues()
    {
        Console.WriteLine("Employee");
        try
        {
            IEnumerable<Issue> issues = await issueLogic.GetIssues();
            return Ok(issues);
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
            throw new Exception(e.Message);
        }
    }
    [HttpGet, Route("Client/Issues/{userId}")]
    public async Task<ActionResult> GetIssuesForUser([FromRoute] int userId)
    {
        Console.WriteLine("Client");
        try
        {
            GetIssuesDTO dto = new GetIssuesDTO
            {
                Id = userId
            };
            IEnumerable<Issue> issues = await issueLogic.GetIssuesForUser(dto);
            return Ok(issues);
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
            throw new Exception(e.Message);
        }
    }
    

    [HttpPost, Route("Message")]
    public async Task<ActionResult> SendMessage([FromBody] SendMessageDTO dto)
    {
        try
        {
            await issueLogic.SendMessage(dto);
            return Ok("Message sent");
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
            throw new Exception(e.Message);
        }
    }

    [HttpGet, Route("Message/{issueId}")]
    public async Task<ActionResult<IEnumerable<Message>>> GetMessagesForIssue([FromRoute] int issueId)
    {
        try
        {
            GetMessagesDTO dto = new GetMessagesDTO() { Id = issueId };
            IEnumerable<Message> messages = await issueLogic.GetMessagesForIssue(dto);
            return Ok(messages);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            return BadRequest(e.Message);
        }
    }
    
    


}