package Database.DAOs;

import Database.DAOs.Interfaces.ChatDaoInterface;
import Database.DAOs.Interfaces.LoginDaoInterface;
import Database.DTOs.IssueCreationDTO;
import Database.DTOs.IssueinfoDTO;
import Database.DTOs.MessageDTO;
import Database.DTOs.UserInfoEmailDTO;
import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

public class ChatDaoTest {
    @InjectMocks
    private ChatDaoInterface dao;
    @Mock
    private SQLConnectionInterface connection;
    @InjectMocks
    private IssueCreationDTO issueCreationDTO;
    @InjectMocks
    private IssueinfoDTO issueinfoDTO;
    @InjectMocks
    private MessageDTO messageDTO;

    @BeforeEach
    void setup()
    {
        dao = new ChatDao();
        connection = Mockito.mock(SQLConnection.class);
        issueCreationDTO = Mockito.mock(IssueCreationDTO.class);
        issueinfoDTO = Mockito.mock(IssueinfoDTO.class);
        messageDTO = Mockito.mock(MessageDTO.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createIssue_calls_SQLConnection() throws SQLException {
        dao.createIssue(issueCreationDTO);
        Mockito.verify(connection).createIssue(issueCreationDTO);
    }

    @Test
    void sendMessage_connection_called() throws SQLException {
        dao.sendMessage(messageDTO);
        Mockito.verify(connection).sendMessage(messageDTO);
    }

    @Test
    void getMessagesForIssue_connection_called() throws SQLException {
        dao.getMessagesForIssue(issueinfoDTO);
        Mockito.verify(connection).getMessagesForIssue(issueinfoDTO);
    }

    @Test
    void getAllIssues_connection_called() throws SQLException {
        dao.getAllIssues();
        Mockito.verify(connection).getAllIssues();
    }

    @Test
    void getMessagesByIssueId_connection_called() throws SQLException {
        dao.getMessagesByIssueId(issueinfoDTO);
        Mockito.verify(connection).getMessagesByIssueId(issueinfoDTO);
    }

}
