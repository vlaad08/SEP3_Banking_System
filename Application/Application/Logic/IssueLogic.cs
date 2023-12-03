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
    public async Task<IEnumerable<Message>> GetMessagesForIssue(IssueGetterDTO dto)
    {
        return await issueDao.GetMessagesForIssue(dto);
    }

    public async Task CreateIssue(IssueCreationDTO dto)
    {
         await issueDao.CreateIssue(dto);
    }
}