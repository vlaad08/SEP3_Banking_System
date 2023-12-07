package Database.DAOs;
import Database.DTOs.*;
import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;
import static org.junit.jupiter.api.Assertions.*;
import Database.UserAccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.SQLException;

import static org.mockito.Mockito.*;
public class RegisterDaoTest {

  @Mock
  private SQLConnectionInterface connection;
  @InjectMocks
  private UserInfoEmailDTO userInfoEmailDTO;
  @InjectMocks
  private UserAccountRequestDTO userAccountRequestDTO;
  @InjectMocks
  private UserAccountDTO userAccountDTO;

  @InjectMocks
  private RegisterRequestDTO registerRequestDTO;

  @InjectMocks
  private RegisterDao dao;


  @BeforeEach
  void setUp() {
    dao = new RegisterDao();
    connection = Mockito.spy(SQLConnection.class);
    userAccountRequestDTO = Mockito.mock(UserAccountRequestDTO.class);
    userInfoEmailDTO = Mockito.mock(UserInfoEmailDTO.class);
    userAccountDTO = Mockito.mock(UserAccountDTO.class);
    registerRequestDTO = Mockito.mock(RegisterRequestDTO.class);
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void register_user_is_called() throws SQLException
  {
    dao.registerUser(registerRequestDTO);
    Mockito.verify(connection).registerUser(registerRequestDTO);
  }

  @Test
  void register_user_is_called_throws_exception() throws SQLException
  {
    doThrow(SQLException.class).when(connection).registerUser(registerRequestDTO);


    assertThrows(SQLException.class, () -> {
      dao.registerUser(registerRequestDTO);
    });
  }

  @Test
  void get_back_users_id() throws SQLException
  {
    Mockito.when(connection.getUserID(userAccountRequestDTO)).thenReturn(1);
    dao.getUserID(userAccountRequestDTO);
    Mockito.verify(connection).getUserID(userAccountRequestDTO);
  }


  @Test
  void getting_back_users_id_throws_exception() throws SQLException
  {
    Mockito.when(connection.getUserID(userAccountRequestDTO)).thenThrow(SQLException.class);

    assertThrows(SQLException.class, () ->
    {
      dao.getUserID(userAccountRequestDTO);
    });
    Mockito.verify(connection).getUserID(userAccountRequestDTO);
  }


  @Test
  void get_back_users_email() throws SQLException{
    Mockito.when(connection.getUserEmail(userAccountRequestDTO)).thenReturn("test@gmail.com");
    assertEquals("test@gmail.com", dao.getUserEmail(userAccountRequestDTO));
    Mockito.verify(connection).getUserEmail(userAccountRequestDTO);
  }


  @Test
  void getting_back_users_email_returns_null() throws SQLException{

    Mockito.when(connection.getUserEmail(userAccountRequestDTO)).thenReturn(null);
    assertNull(dao.getUserEmail(userAccountRequestDTO));
    Mockito.verify(connection).getUserEmail(userAccountRequestDTO);
  }

  @Test
  void getting_back_user_throws_exception() throws SQLException{
    Mockito.when(connection.getUserEmail(userAccountRequestDTO)).thenThrow(SQLException.class);

    assertThrows(SQLException.class, () -> {
      dao.getUserEmail(userAccountRequestDTO);
    });

    Mockito.verify(connection).getUserEmail(userAccountRequestDTO);
  }

  @Test
  void generate_new_account_number() throws SQLException
  {
    dao.generateAccountNumber(userAccountDTO);
    Mockito.verify(connection).generateAccountNumber(userAccountDTO);
  }

  @Test
  void generating_new_account_number_throws_exception() throws SQLException
  {
    doThrow(SQLException.class).when(connection).generateAccountNumber(userAccountDTO);

    assertThrows(SQLException.class, () -> {
      dao.generateAccountNumber(userAccountDTO);
    });
    Mockito.verify(connection).generateAccountNumber(userAccountDTO);
  }

}
