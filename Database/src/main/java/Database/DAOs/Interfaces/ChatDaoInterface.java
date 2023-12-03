package Database.DAOs.Interfaces;

import Database.DTOs.IssueDTO;
import Database.DTOs.MessageDTO;

import java.sql.SQLException;

public interface ChatDaoInterface {
    void createIssue(IssueDTO issueDTO) throws SQLException;
    void sendMessage(MessageDTO messageDTO) throws SQLException;
    void getMessagesForIssue() throws SQLException;
}
