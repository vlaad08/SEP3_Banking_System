package Database.DAOs;

import Database.AccountsInfo;
import Database.DAOs.Interfaces.LoginDaoInterface;
import Database.DTOs.UserInfoDTO;
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
    @InjectMocks
    private UserInfoDTO userInfoDTO;

    @BeforeEach
    void setup()
    {
        dao = new LoginDao();
        connection = Mockito.mock(SQLConnection.class);
        userInfoDTO = new UserInfoDTO("testmail@test.test");

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

    @Test
    void getUsers_called_in_sqlconnection() throws SQLException {
        dao.getUsers();
        Mockito.verify(connection).getUsers();
    }

    @Test
    void getAccountsInfo_returns_a_list() throws SQLException {
        AccountsInfo temp = AccountsInfo.newBuilder().
                setAccountBalance(10)
                .setAccountNumber("11111111111111")
                .setAccountType("personal")
                .setOwnerName("Name")
                .build();
        Mockito.when(connection.getAccountsInfo()).thenReturn(List.of(temp));
        assertTrue(connection.getAccountsInfo() instanceof List);
    }

    @Test
    void getAccountsInfo_called_in_SQLConnection() throws SQLException {
        dao.getAccountsInfo();
        Mockito.verify(connection).getAccountsInfo();
    }

    @Test
    void getUserAccountInfos_returns_a_list() throws SQLException {
        AccountsInfo temp = AccountsInfo.newBuilder().
                setAccountBalance(10)
                .setAccountNumber("11111111111111")
                .setAccountType("personal")
                .setOwnerName("Name")
                .build();

        Mockito.when(connection.getUserAccountInfos(userInfoDTO)).thenReturn(List.of(temp));
        assertTrue(connection.getUserAccountInfos(userInfoDTO) instanceof List);
    }

    @Test
    void getUserAccountInfos_called_in_SQLConnection() throws SQLException {
        dao.getUserAccountInfos(userInfoDTO);
        Mockito.verify(connection).getUserAccountInfos(userInfoDTO);
    }

}
