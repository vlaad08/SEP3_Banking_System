package Database.Server;

import Database.*;
import Database.DAOs.Interfaces.LoginDaoInterface;
import Database.DAOs.Interfaces.TransactionDaoInterface;
import Database.DAOs.LoginDao;
import Database.DAOs.TransactionDao;
import Database.DTOs.CheckAccountDTO;
import Database.DTOs.DepositRequestDTO;
import Database.DTOs.TransferRequestDTO;
import io.grpc.stub.StreamObserver;
import io.grpc.stub.StreamObservers;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.internal.MockitoCore;

import javax.annotation.meta.When;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


public class GRPCServerImpTest {
    @InjectMocks
    private GRPCServerImp grpcServerImp;
    @Mock
    private TransactionDaoInterface dao;
    @Mock
    private LoginDaoInterface loginDao;
    @Captor
    private ArgumentCaptor<TransferRequestDTO> transferCaptor;
    @Captor
    private ArgumentCaptor<CheckAccountDTO> accountCheckCaptor;
    @Captor
    private ArgumentCaptor<DepositRequestDTO> depositCaptor;

    @BeforeEach
    void setup() {
        grpcServerImp = new GRPCServerImp();
        dao = Mockito.mock(TransactionDao.class);
        loginDao = Mockito.mock(LoginDao.class);

        transferCaptor = ArgumentCaptor.forClass(TransferRequestDTO.class);
        accountCheckCaptor = ArgumentCaptor.forClass(CheckAccountDTO.class);
        depositCaptor = ArgumentCaptor.forClass(DepositRequestDTO.class);
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

        Mockito.verify(dao).makeTransfer(transferCaptor.capture());
        Mockito.verify(responseObserver).onNext(Mockito.any());
        Mockito.verify(responseObserver).onCompleted();
        assertEquals("aaaabbbbccccdddd",transferCaptor.getValue().getSenderAccount_id());
        assertEquals("bbbbaaaaccccdddd",transferCaptor.getValue().getRecipientAccount_id());
        assertEquals(50,transferCaptor.getValue().getAmount());
        assertEquals("-",transferCaptor.getValue().getMessage());
    }

    @Test
    void checkAccount_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<AccountCheckResponse> responseObserver = Mockito.mock(StreamObserver.class);
        AccountCheckRequest accountCheckRequest = AccountCheckRequest.newBuilder()
                .setRecipientAccountId("bbbbaaaaccccdddd")
                .build();
        try
        {
            grpcServerImp.checkAccount(accountCheckRequest,responseObserver);
        }catch (NullPointerException e)
        {}
        Mockito.verify(dao).checkAccountId(accountCheckCaptor.capture());
        Mockito.verify(responseObserver).onNext(Mockito.any());
        Mockito.verify(responseObserver).onCompleted();
        assertEquals("bbbbaaaaccccdddd",accountCheckCaptor.getValue().getRecipientAccount_id());
    }

    @Test
    void checkBalance_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<BalanceCheckResponse> responseObserver = Mockito.mock(StreamObserver.class);
        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountId("bbbbaaaaccccdddd")
                .build();
        grpcServerImp.checkBalance(balanceCheckRequest,responseObserver);

        Mockito.verify(dao).checkBalance(accountCheckCaptor.capture());
        Mockito.verify(responseObserver).onNext(Mockito.any());
        Mockito.verify(responseObserver).onCompleted();
        assertEquals("bbbbaaaaccccdddd",accountCheckCaptor.getValue().getRecipientAccount_id());
    }

    @Test
    void dailyCheck_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<DailyCheckResponse> responseObserver = Mockito.mock(StreamObserver.class);
        DailyCheckRequest accountCheckRequest = DailyCheckRequest.newBuilder()
                .setAccountId("bbbbaaaaccccdddd")
                .build();
        grpcServerImp.dailyCheckTransactions(accountCheckRequest,responseObserver);

        Mockito.verify(dao).dailyCheck(accountCheckCaptor.capture());
        Mockito.verify(responseObserver).onNext(Mockito.any());
        Mockito.verify(responseObserver).onCompleted();
        assertEquals("bbbbaaaaccccdddd",accountCheckCaptor.getValue().getRecipientAccount_id());
    }

    @Test
    void deposit_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<DepositResponse> responseObserver = Mockito.mock(StreamObserver.class);
        DepositRequest depositRequest = DepositRequest.newBuilder()
                .setAccountId("bbbbaaaaccccdddd")
                .setAmount(50)
                .build();
        grpcServerImp.deposit(depositRequest,responseObserver);

        Mockito.verify(dao).makeDeposit(depositCaptor.capture());
        Mockito.verify(responseObserver).onNext(Mockito.any());
        Mockito.verify(responseObserver).onCompleted();
        assertEquals("bbbbaaaaccccdddd",depositCaptor.getValue().getAccount_id());
        assertEquals(50,depositCaptor.getValue().getAmount());
    }

    @Test
    void loginValidation_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<LoginValidationResponse> responseObserver = Mockito.mock(StreamObserver.class);
        LoginValidationRequest loginRequest = LoginValidationRequest.newBuilder().build();
        grpcServerImp.loginValidation(loginRequest,responseObserver);

        Mockito.verify(loginDao).getUsers();
        Mockito.verify(responseObserver).onNext(Mockito.any());
        Mockito.verify(responseObserver).onCompleted();
    }
}
