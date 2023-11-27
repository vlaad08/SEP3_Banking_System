package Database.DAOs;

import Database.DAOs.Interfaces.LoginDaoInterface;
import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;
import Database.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDaoTest {
    @InjectMocks
    private LoginDaoInterface dao;
    @Mock
    private SQLConnectionInterface connection;

    @BeforeEach
    void setup()
    {
        dao = new LoginDao();
        connection = Mockito.mock(SQLConnection.class);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsers_returns_a_List() throws SQLException {
        User sampleUser = User.newBuilder()
                .setEmail("test@example.com")
                .setPassword("test1234")
                .setFirstName("Test")
                .setMiddleName("Test")
                .setLastName("Test")
                .setRole("testUser")
                .build();
        Mockito.when(connection.getUsers()).thenReturn(List.of(sampleUser));
        assertTrue(connection.getUsers() instanceof List);
    }

    @Test void getUsers_called_in_sqlconnection() throws SQLException {
        dao.getUsers();
        Mockito.verify(connection).getUsers();
    }
}
