package Database.DAOs;

import Database.DTOs.AccountNewBaseRateDTO;
import Database.DTOs.UserNewDetailsRequestDTO;
import Database.DataAccess.SQLConnection;
import Database.DataAccess.SQLConnectionInterface;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

public class CredentialChangerDaoTest
{
  @InjectMocks
  private CredentialChangerDao dao;

  @Mock
  private SQLConnectionInterface connection;


  @InjectMocks
  private AccountNewBaseRateDTO accountNewBaseRateDTO;
  @InjectMocks
  private UserNewDetailsRequestDTO userNewDetailsRequestDTO;



  @BeforeEach
  void setUp()
  {
    connection = Mockito.spy(SQLConnection.class);
    dao = new CredentialChangerDao();
    accountNewBaseRateDTO = new AccountNewBaseRateDTO(1, 3.7);
    userNewDetailsRequestDTO = new UserNewDetailsRequestDTO("newemail@gmail.com", "oldemail@gmail.com", "12345678"
    , "Premium");
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void updating_new_base_rate() throws SQLException
  {
    dao.UpdateBaseRate(accountNewBaseRateDTO);
    Mockito.verify(connection).updateNewBaseRate(accountNewBaseRateDTO);
  }

  @Test
  void updating_new_base_rate_throws_exception() throws SQLException
  {
    doThrow(SQLException.class).when(connection).updateNewBaseRate(accountNewBaseRateDTO);

    assertThrows(SQLException.class, () -> connection.updateNewBaseRate(accountNewBaseRateDTO));
  }

  @Test
  void updating_user_details() throws SQLException{
    dao.UpdateUserInformation(userNewDetailsRequestDTO);
    Mockito.verify(connection).updateUserInformation(userNewDetailsRequestDTO);
  }

  @Test
  void updating_user_information_throws_exception() throws SQLException
  {
    doThrow(SQLException.class).when(connection).updateUserInformation(userNewDetailsRequestDTO);
    assertThrows(SQLException.class, () -> connection.updateUserInformation(userNewDetailsRequestDTO));
  }
}
