using Application.DaoInterfaces;
using Application.Logic;
using Database;
using Domain.DTOs;
using Domain.Models;
using Grpc.DAOs;
using Moq;
using Issue = Domain.Models.Issue;

namespace Tests.IssueTest;

public class IssueLogicTest
{
    [Fact]
    public async Task SendMessage_Calls_For_Dao()
    {
        SendMessageDTO dto = new SendMessageDTO("T", "B", 1, 3);
        var issueDao = new Mock<IIssueDAO>();
        var issueLogic = new IssueLogic(issueDao.Object);
        await issueLogic.SendMessage(dto);
        issueDao.Verify(d =>d.SendMessage(dto));
    }
    [Fact]
    public async Task CreateIssue_Calls_For_Dao()
    {
        IssueCreationDTO dto = new IssueCreationDTO
        {
            Title = "t",
            Body = "b",
            Owner = 3
        };
        var issueDao = new Mock<IIssueDAO>();
        var issueLogic = new IssueLogic(issueDao.Object);
        await issueLogic.CreateIssue(dto);
        issueDao.Verify(d =>d.CreateIssue(dto));
    }
    [Fact]
    public async Task GetIssues_Calls_For_Dao()
    {
        var issueDao = new Mock<IIssueDAO>();
        var issueLogic = new IssueLogic(issueDao.Object);
        await issueLogic.GetIssues();
        issueDao.Verify(d =>d.GetIssues());
    }
    [Fact]
    public async Task GetIssuesForUser_Calls_For_Dao()
    {
        GetIssuesDTO dto = new GetIssuesDTO
        {
            Id = 3
        };
        var issueDao = new Mock<IIssueDAO>();
        var issueLogic = new IssueLogic(issueDao.Object);
        await issueLogic.GetIssuesForUser(dto);
        issueDao.Verify(d =>d.GetIssues());
    }
    [Fact]
    public async Task GetIssuesForUser_Returns_A_List()
    {
        GetIssuesDTO dto = new GetIssuesDTO
        {
            Id = 3
        };
        var issueDao = new Mock<IIssueDAO>();
        var issueLogic = new IssueLogic(issueDao.Object);
        IEnumerable<Issue> issues = await issueLogic.GetIssuesForUser(dto);
        Assert.Equal(issues.GetType(), typeof(List<Issue>));
    }
    [Fact]
    public async Task GetMessagesFoIssue_Returns_A_List()
    {
        GetMessagesDTO dto = new GetMessagesDTO()
        {
            Id = 3
        };
        var issueDao = new Mock<IIssueDAO>();
        var issueLogic = new IssueLogic(issueDao.Object);
        IEnumerable<Message> messages = await issueLogic.GetMessagesForIssue(dto);
        Assert.Equal(messages.GetType(), typeof(Message[]));
    }
}