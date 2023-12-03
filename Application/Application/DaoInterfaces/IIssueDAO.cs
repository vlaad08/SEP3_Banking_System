using Domain.DTOs;
using Domain.Models;

namespace Application.DaoInterfaces;

public interface IIssueDAO
{
    Task SendMessage(SendMessageDTO dto);
    Task<IEnumerable<Message>> GetMessagesForIssue(IssueGetterDTO dto);
    Task CreateIssue(IssueCreationDTO dto);
}