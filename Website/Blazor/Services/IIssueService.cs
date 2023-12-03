using Shared.DTOs;

namespace Blazor.Services;

public interface IIssueService
{
   public Task CreateIssue(IssueCreationDto issueCreationDto);
}