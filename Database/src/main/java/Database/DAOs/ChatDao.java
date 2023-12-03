package Database.DAOs;

import Database.DAOs.Interfaces.ChatDaoInterface;
import Database.DTOs.IssueDTO;
import Database.DTOs.MessageDTO;
import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;
import jdk.jshell.spi.ExecutionControl;

import java.sql.SQLException;

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
    public void createIssue(IssueDTO issueDTO) throws SQLException {
        connection.createIssue(issueDTO);
    }

    @Override
    public void sendMessage(MessageDTO messageDTO) throws SQLException {
        connection.sendMessage(messageDTO);
    }

    @Override
    public void getMessagesForIssue() throws SQLException {

    }


}
