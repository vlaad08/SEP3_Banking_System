using Domain.DTOs;
using Domain.Models;

namespace Application.LogicInterfaces;

public interface IIssueLogic
{ 
    Task SendMessage(SendMessageDTO dto);
    Task<IEnumerable<Message>> GetMessagesForIssue(GetMessagesDTO dto);
    Task CreateIssue(IssueCreationDTO dto);
    Task UpdateIssue(IssueUpdateDTO dto);
    Task<IEnumerable<Issue>> GetIssues();
    Task<IEnumerable<Issue>> GetIssuesForUser(GetIssuesDTO dto);
}