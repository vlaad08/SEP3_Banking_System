using Domain.DTOs;
using Domain.Models;

namespace Application.LogicInterfaces;

public interface IIssueLogic
{ 
    Task SendMessage(SendMessageDTO dto);
    Task<IEnumerable<Message>> GetMessagesForIssue(IssueGetterDTO dto);
    Task CreateIssue(IssueCreationDTO dto);

}