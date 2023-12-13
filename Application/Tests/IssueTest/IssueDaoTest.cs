using Application.DaoInterfaces;
using Application.Logic;
using Domain.DTOs;
using Grpc;
using Grpc.DAOs;
using Moq;

namespace Tests.IssueTest;

public class IssueDaoTest
{
    [Fact]
    public async Task SendMessages_Calls_For_Grpc()
    {
        SendMessageDTO dto = new SendMessageDTO("T", "B", 1, 3);
        var grpcClient = new Mock<IGrpcClient>();
        var issueDao = new IssueDao(grpcClient.Object);
        await issueDao.SendMessage(dto);
        grpcClient.Verify(g =>g.SendMessage(dto));
    }
    [Fact]
    public async Task GetMessagesForIssue_Calls_For_Grpc()
    {
        GetMessagesDTO dto = new GetMessagesDTO
        {
            Id = 1
        };
        var grpcClient = new Mock<IGrpcClient>();
        var issueDao = new IssueDao(grpcClient.Object);
        await issueDao.GetMessagesForIssue(dto);
        grpcClient.Verify(g =>g.GetMessagesForIssue(dto));
    }
    [Fact]
    public async Task CreateIssue_Calls_For_Grpc()
    {
        
        IssueCreationDTO dto = new IssueCreationDTO
        {
            Title = "t",
            Body = "b",
            Owner = 3
        };
        var grpcClient = new Mock<IGrpcClient>();
        var issueDao = new IssueDao(grpcClient.Object);
        await issueDao.CreateIssue(dto);
        grpcClient.Verify(g =>g.CreateIssue(dto));
    }
    [Fact]
    public async Task GetIssues_Calls_For_Grpc()
    {
        var grpcClient = new Mock<IGrpcClient>();
        var issueDao = new IssueDao(grpcClient.Object);
        await issueDao.GetIssues();
        grpcClient.Verify(g =>g.GetIssues());
    }

    [Fact]
    public async Task UpdateIssue_Calls_for_Grpc()
    {
        var grpcClient = new Mock<IGrpcClient>();
        var issueDao = new IssueDao(grpcClient.Object);
        await issueDao.UpdateIssue(It.IsAny<IssueUpdateDTO>());
        grpcClient.Verify(g =>g.UpdateIssue(It.IsAny<IssueUpdateDTO>()));

    }
}