package Database.DAOs.Interfaces;

import Database.DTOs.IssueCreationDTO;
import Database.DTOs.IssueUpdateDTO;
import Database.DTOs.IssueinfoDTO;
import Database.DTOs.MessageDTO;
import Database.Issue;
import Database.MessageInfo;

import java.sql.SQLException;
import java.util.List;

public interface ChatDaoInterface {
    void createIssue(IssueCreationDTO issueDTO) throws SQLException;
    void updateIssue(IssueUpdateDTO issueDTO) throws SQLException;
    void sendMessage(MessageDTO messageDTO) throws SQLException;
    List<MessageInfo> getMessagesForIssue(IssueinfoDTO issueinfoDTO) throws SQLException;
    /*void getIssueByUserId() throws SQLException;*/
    List<Issue> getAllIssues() throws  SQLException;
    List<MessageInfo> getMessagesByIssueId(IssueinfoDTO issueinfoDTO) throws SQLException;
}
