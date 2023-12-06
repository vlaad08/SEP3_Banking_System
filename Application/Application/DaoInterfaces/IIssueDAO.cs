using Domain.DTOs;
using Domain.Models;

namespace Application.DaoInterfaces;

public interface IIssueDAO
{
    Task SendMessage(SendMessageDTO dto);
    Task<IEnumerable<Message>> GetMessagesForIssue(GetMessagesDTO dto);
    Task CreateIssue(IssueCreationDTO dto);
    Task UpdateIssue(IssueUpdateDTO dto);
    Task<IEnumerable<Issue>> GetIssues();
}