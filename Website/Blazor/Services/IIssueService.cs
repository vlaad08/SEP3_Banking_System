using Shared.DTOs;
using Shared.Models;

namespace Blazor.Services;

public interface IIssueService
{
   public Task CreateIssue(IssueCreationDto issueCreationDto);
   public Task SendMessage(SendMessageDto sendMessageDto);
   public Task<IEnumerable<Message>> GetMessagesForIssue(GetMessagesDto getMessagesDto);
   public Task<IEnumerable<Issue>> GetIssues();
   public Task<IEnumerable<Issue>> GetIssuesForClient(GetIssuesDto dto);
}