package Database.DAOs;

import Database.DAOs.Interfaces.ChatDaoInterface;
import Database.DTOs.IssueCreationDTO;
import Database.DTOs.IssueUpdateDTO;
import Database.DTOs.IssueinfoDTO;
import Database.DTOs.MessageDTO;
import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;
import Database.Issue;
import Database.MessageInfo;

import java.sql.SQLException;
import java.util.List;

public class ChatDao implements ChatDaoInterface {
    SQLConnectionInterface connection;
    {
        try {
            connection = SQLConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createIssue(IssueCreationDTO issueDTO) throws SQLException {
        connection.createIssue(issueDTO);
    }
    @Override
    public void updateIssue(IssueUpdateDTO issueDTO) throws SQLException {
        connection.updateIssue(issueDTO);
    }

    @Override
    public void sendMessage(MessageDTO messageDTO) throws SQLException {
        connection.sendMessage(messageDTO);
    }

    @Override
    public List<MessageInfo> getMessagesForIssue(IssueinfoDTO issueinfoDTO) throws SQLException {
        return connection.getMessagesForIssue(issueinfoDTO);
    }

    @Override
    public List<Issue> getAllIssues() throws SQLException {
        return connection.getAllIssues();
    }

    @Override
    public List<MessageInfo> getMessagesByIssueId(IssueinfoDTO issueinfoDTO) throws SQLException{
        return connection.getMessagesByIssueId(issueinfoDTO);
    }

}
