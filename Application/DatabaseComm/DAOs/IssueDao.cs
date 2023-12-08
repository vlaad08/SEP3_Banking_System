using Application.DaoInterfaces;
using Domain.DTOs;
using Domain.Models;

namespace Grpc.DAOs;

public class IssueDao : IIssueDAO
{
    private readonly IGrpcClient grpcClient;
    public IssueDao(IGrpcClient grpcClient)
    {
        this.grpcClient = grpcClient;
    }
    public async Task SendMessage(SendMessageDTO dto)
    {
        await grpcClient.SendMessage(dto);
    }
    public async Task<IEnumerable<Message>> GetMessagesForIssue(GetMessagesDTO dto)
    {
        return await grpcClient.GetMessagesForIssue(dto);
    }

    public async Task CreateIssue(IssueCreationDTO dto)
    {
        await grpcClient.CreateIssue(dto);
    }
    public async Task UpdateIssue(IssueUpdateDTO dto)
    {
        await grpcClient.UpdateIssue(dto);
    }

    public async Task<IEnumerable<Issue>> GetIssues()
    {
        return await grpcClient.GetIssues();
    }
}