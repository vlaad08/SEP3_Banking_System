package Database.Server;

import Database.*;
import Database.DAOs.ChatDao;
import Database.DAOs.Interfaces.ChatDaoInterface;
import Database.DAOs.Interfaces.LoginDaoInterface;
import Database.DAOs.Interfaces.TransactionDaoInterface;
import Database.DAOs.LoginDao;
import Database.DAOs.TransactionDao;
import Database.DTOs.*;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class GRPCServerImpTest {
    @InjectMocks
    private GRPCServerImp grpcServerImp;
    @Mock
    private TransactionDaoInterface transactionDao;
    @Mock
    private LoginDaoInterface loginDao;
    @Mock
    private ChatDaoInterface chatDao;
    @Captor
    private ArgumentCaptor<TransferRequestDTO> transferCaptor;
    @Captor
    private ArgumentCaptor<CheckAccountDTO> accountCheckCaptor;
    @Captor
    private ArgumentCaptor<DepositRequestDTO> depositCaptor;
    @Captor
    private ArgumentCaptor<UserInfoEmailDTO> userInfoEmailDto;
    @Captor
    private ArgumentCaptor<UserInfoAccNumDTO> userInfoAccNumDto;

    @BeforeEach
    void setup() {
        grpcServerImp = new GRPCServerImp();
        transactionDao = Mockito.mock(TransactionDao.class);
        loginDao = Mockito.mock(LoginDao.class);
        chatDao = Mockito.mock(ChatDao.class);

        transferCaptor = ArgumentCaptor.forClass(TransferRequestDTO.class);
        accountCheckCaptor = ArgumentCaptor.forClass(CheckAccountDTO.class);
        depositCaptor = ArgumentCaptor.forClass(DepositRequestDTO.class);
        userInfoEmailDto = ArgumentCaptor.forClass(UserInfoEmailDTO.class);
        userInfoAccNumDto = ArgumentCaptor.forClass(UserInfoAccNumDTO.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void transfer_calls_dao_and_sends_response() {
        StreamObserver<TransferResponse> responseObserver = Mockito.mock(StreamObserver.class);
        TransferRequest transferRequest = TransferRequest.newBuilder()
                .setSenderAccountId("aaaabbbbccccdddd")
                .setRecipientAccountId("bbbbaaaaccccdddd")
                .setBalance(50)
                .setMessage("-")
                .build();

        grpcServerImp.transfer(transferRequest, responseObserver);

        Mockito.verify(transactionDao).makeTransfer(transferCaptor.capture());
        Mockito.verify(responseObserver).onNext(Mockito.any());
        Mockito.verify(responseObserver).onCompleted();
        assertEquals("aaaabbbbccccdddd", transferCaptor.getValue().getSenderAccount_id());
        assertEquals("bbbbaaaaccccdddd", transferCaptor.getValue().getRecipientAccount_id());
        assertEquals(50, transferCaptor.getValue().getAmount());
        assertEquals("-", transferCaptor.getValue().getMessage());
    }

    // @Test
    // void checkAccount_calls_dao_and_sends_response() throws SQLException {
    // StreamObserver<AccountCheckResponse> responseObserver =
    // Mockito.mock(StreamObserver.class);
    // AccountCheckRequest accountCheckRequest = AccountCheckRequest.newBuilder()
    // .setRecipientAccountId("bbbbaaaaccccdddd")
    // .build();
    // try
    // {
    // grpcServerImp.checkAccount(accountCheckRequest,responseObserver);
    // }catch (NullPointerException e)
    // {}
    // Mockito.verify(dao).checkAccountId(accountCheckCaptor.capture());
    // Mockito.verify(responseObserver).onNext(Mockito.any());
    // Mockito.verify(responseObserver).onCompleted();
    // assertEquals("bbbbaaaaccccdddd",accountCheckCaptor.getValue().getRecipientAccount_id());
    // }
    @Test
    void checkAccount_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<AccountCheckResponse> responseObserver = Mockito.mock(StreamObserver.class);
        AccountCheckRequest accountCheckRequest = AccountCheckRequest.newBuilder()
                .setRecipientAccountId("bbbbaaaaccccdddd")
                .build();
        try {
            grpcServerImp.checkAccount(accountCheckRequest, responseObserver);
        } catch (NullPointerException e) {
        }
        Mockito.verify(transactionDao).checkAccountId(accountCheckCaptor.capture());
        Mockito.verify(responseObserver).onNext(Mockito.any());
        Mockito.verify(responseObserver).onCompleted();
        assertEquals("bbbbaaaaccccdddd", accountCheckCaptor.getValue().getRecipientAccount_id());
    }

    @Test
    void checkBalance_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<BalanceCheckResponse> responseObserver = Mockito.mock(StreamObserver.class);
        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountId("bbbbaaaaccccdddd")
                .build();
        grpcServerImp.checkBalance(balanceCheckRequest, responseObserver);

        Mockito.verify(transactionDao).checkBalance(accountCheckCaptor.capture());
        Mockito.verify(responseObserver).onNext(Mockito.any());
        Mockito.verify(responseObserver).onCompleted();
        assertEquals("bbbbaaaaccccdddd", accountCheckCaptor.getValue().getRecipientAccount_id());
    }

    @Test
    void dailyCheck_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<DailyCheckResponse> responseObserver = Mockito.mock(StreamObserver.class);
        DailyCheckRequest accountCheckRequest = DailyCheckRequest.newBuilder()
                .setAccountId("bbbbaaaaccccdddd")
                .build();
        grpcServerImp.dailyCheckTransactions(accountCheckRequest, responseObserver);

        Mockito.verify(transactionDao).dailyCheck(accountCheckCaptor.capture());
        Mockito.verify(responseObserver).onNext(Mockito.any());
        Mockito.verify(responseObserver).onCompleted();
        assertEquals("bbbbaaaaccccdddd", accountCheckCaptor.getValue().getRecipientAccount_id());
    }

    @Test
    void deposit_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<DepositResponse> responseObserver = Mockito.mock(StreamObserver.class);
        DepositRequest depositRequest = DepositRequest.newBuilder()
                .setAccountId("bbbbaaaaccccdddd")
                .setAmount(50)
                .build();
        grpcServerImp.deposit(depositRequest, responseObserver);

        Mockito.verify(transactionDao).makeDeposit(depositCaptor.capture());
        Mockito.verify(responseObserver).onNext(Mockito.any());
        Mockito.verify(responseObserver).onCompleted();
        assertEquals("bbbbaaaaccccdddd", depositCaptor.getValue().getAccount_id());
        assertEquals(50, depositCaptor.getValue().getAmount());
    }

    @Test
    void loginValidation_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<LoginValidationResponse> responseObserver = Mockito.mock(StreamObserver.class);
        LoginValidationRequest loginRequest = LoginValidationRequest.newBuilder().build();
        grpcServerImp.loginValidation(loginRequest, responseObserver);

        Mockito.verify(loginDao).getUsers();
        Mockito.verify(responseObserver).onNext(Mockito.any());
        Mockito.verify(responseObserver).onCompleted();
    }

    @Test
    void allAccountsInfo_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<AllAccountsInfoResponse> response = Mockito.mock(StreamObserver.class);
        AllAccountsInfoRequest infoRequest = AllAccountsInfoRequest.newBuilder().build();
        grpcServerImp.allAccountsInfo(infoRequest, response);

        Mockito.verify(loginDao).getAccountsInfo();
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
    }

    @Test
    void userAccountsInfo_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<UserAccountInfoResponse> response = Mockito.mock(StreamObserver.class);
        UserAccountInfoRequest infoRequest = UserAccountInfoRequest.newBuilder().setEmail("totlevente@gmail.com")
                .build();
        grpcServerImp.userAccountsInfo(infoRequest, response);

        Mockito.verify(loginDao).getUserAccountInfos(userInfoEmailDto.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals("totlevente@gmail.com", userInfoEmailDto.getValue().getEmail());
    }

    @Test
    void creditInterest_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<CreditInterestResponse> response = Mockito.mock(StreamObserver.class);
        CreditInterestRequest request = CreditInterestRequest.newBuilder().setAccountNumber("aaaabbbbccccdddd").build();
        grpcServerImp.creditInterest(request, response);

        Mockito.verify(transactionDao).creditInterest(userInfoAccNumDto.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals("aaaabbbbccccdddd", userInfoAccNumDto.getValue().getAccNum());
    }

    @Test
    void lastInterest_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<LastInterestResponse> response = Mockito.mock(StreamObserver.class);
        LastInterestRequest request = LastInterestRequest.newBuilder().setAccountNumber("aaaabbbbccccdddd").build();
        grpcServerImp.lastInterest(request, response);

        Mockito.verify(transactionDao).lastInterest(userInfoAccNumDto.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals("aaaabbbbccccdddd", userInfoAccNumDto.getValue().getAccNum());
    }

}
