using System.Formats.Asn1;
using Application.DaoInterfaces;
using Application.LogicInterfaces;
using Domain.DTOs;
using Domain.Models;

namespace Application.Logic;

public class IssueLogic : IIssueLogic
{
    private readonly IIssueDAO issueDao;

    public IssueLogic(IIssueDAO issueDao)
    {
        this.issueDao = issueDao;
    }

    public async Task SendMessage(SendMessageDTO dto)
    {
        await issueDao.SendMessage(dto);
    }
    public async Task<IEnumerable<Message>> GetMessagesForIssue(GetMessagesDTO dto)
    {
        return await issueDao.GetMessagesForIssue(dto);
    }

    public async Task CreateIssue(IssueCreationDTO dto)
    {
         await issueDao.CreateIssue(dto);
    }

    public async Task<IEnumerable<Issue>> GetIssues()
    {
        return await issueDao.GetIssues();
    }

    public async Task<IEnumerable<Issue>> GetIssuesForUser(GetIssuesDTO dto)
    {
        int owner = Convert.ToInt32(dto.Id);
        IEnumerable<Issue> issues = await issueDao.GetIssues();
        List<Issue> newIssues = new List<Issue>();
        foreach (var i in issues)
        {
            if (i.Owner==owner)
            {
                newIssues.Add(i);
            }
        }
        return newIssues;
    }
}