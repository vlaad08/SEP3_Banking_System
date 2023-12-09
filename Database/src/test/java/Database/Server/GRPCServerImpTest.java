package Database.Server;

import Database.*;
import Database.DAOs.*;
import Database.DAOs.Interfaces.ChatDaoInterface;
import Database.DAOs.Interfaces.LoginDaoInterface;
import Database.DAOs.Interfaces.RegisterDaoInterface;
import Database.DAOs.Interfaces.TransactionDaoInterface;
import Database.DTOs.*;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Stream;

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
    @Mock
    private RegisterDaoInterface registerDao;
    @Mock
    private CredentialChangerDao credentialChangerDao;
    @Captor
    private ArgumentCaptor<UpdatedBalancesForTransferDTO> updatedBalancesForTransferDTO;
    @Captor
    private ArgumentCaptor<CheckAccountDTO> accountCheckCaptor;
    @Captor
    private ArgumentCaptor<DepositRequestDTO> depositCaptor;
    @Captor
    private ArgumentCaptor<CreditInterestDTO> creditInterestDTO;
    @Captor
    private ArgumentCaptor<UserInfoEmailDTO> userInfoEmailDto;
    @Captor
    private ArgumentCaptor<UserNewDetailsRequestDTO> userNewDetailsRequestDTO;
    @Captor
    private ArgumentCaptor<IssueUpdateDTO> issueUpdateDTO;
    @Captor
    private ArgumentCaptor<UserNewEmailDTO> userNewEmailDTO;
    @Captor
    private ArgumentCaptor<UserNewPasswordDTO> userNewPasswordDTO;
    private ArgumentCaptor<UserNewPlanDTO> userNewPlanDTO;
    @Captor
    private ArgumentCaptor<FlagUserDTO> flagUserDTO;
    @Captor
    private ArgumentCaptor<UserInfoAccNumDTO> userInfoAccNumDto;
    @Captor
    private ArgumentCaptor<LoanRequestDTO> loanRequestDto;
    @Captor
    private ArgumentCaptor<RegisterRequestDTO> registerRequestDto;
    @Captor
    private ArgumentCaptor<UserAccountRequestDTO> userAccountRequestDto;
    @Captor
    private ArgumentCaptor<UserAccountDTO> userAccountDto;
    @Captor
    private ArgumentCaptor<AccountNewBaseRateDTO> accountNewBaseRateDto;
    @Captor
    private ArgumentCaptor<IssueCreationDTO> issueCreationDto;
    @Captor
    private ArgumentCaptor<MessageDTO> messageDto;
    @Captor
    private ArgumentCaptor<IssueinfoDTO> issueinfoDto;
    @BeforeEach
    void setup() {
        grpcServerImp = new GRPCServerImp();
        transactionDao = Mockito.mock(TransactionDao.class);
        loginDao = Mockito.mock(LoginDao.class);
        chatDao = Mockito.mock(ChatDao.class);
        registerDao = Mockito.mock(RegisterDao.class);
        credentialChangerDao = Mockito.mock(CredentialChangerDao.class);

        updatedBalancesForTransferDTO = ArgumentCaptor.forClass(UpdatedBalancesForTransferDTO.class);
        accountCheckCaptor = ArgumentCaptor.forClass(CheckAccountDTO.class);
        depositCaptor = ArgumentCaptor.forClass(DepositRequestDTO.class);
        creditInterestDTO = ArgumentCaptor.forClass(CreditInterestDTO.class);
        userInfoEmailDto = ArgumentCaptor.forClass(UserInfoEmailDTO.class);
        userInfoAccNumDto = ArgumentCaptor.forClass(UserInfoAccNumDTO.class);
        loanRequestDto = ArgumentCaptor.forClass(LoanRequestDTO.class);
        registerRequestDto = ArgumentCaptor.forClass(RegisterRequestDTO.class);
        userAccountRequestDto = ArgumentCaptor.forClass(UserAccountRequestDTO.class);
        userAccountDto = ArgumentCaptor.forClass(UserAccountDTO.class);
        accountNewBaseRateDto = ArgumentCaptor.forClass(AccountNewBaseRateDTO.class);
        issueCreationDto = ArgumentCaptor.forClass(IssueCreationDTO.class);
        messageDto = ArgumentCaptor.forClass(MessageDTO.class);
        issueinfoDto = ArgumentCaptor.forClass(IssueinfoDTO.class);
        userNewDetailsRequestDTO = ArgumentCaptor.forClass(UserNewDetailsRequestDTO.class);
        issueUpdateDTO = ArgumentCaptor.forClass(IssueUpdateDTO.class);
        userNewEmailDTO = ArgumentCaptor.forClass(UserNewEmailDTO.class);
        userNewPasswordDTO = ArgumentCaptor.forClass(UserNewPasswordDTO.class);
        userNewPlanDTO = ArgumentCaptor.forClass(UserNewPlanDTO.class);
        flagUserDTO = ArgumentCaptor.forClass(FlagUserDTO.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void transfer_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<TransferResponse> responseObserver = Mockito.mock(StreamObserver.class);
        TransferRequest transferRequest = TransferRequest.newBuilder()
                .setSenderId("aaaabbbbccccdddd")
                .setReceiverId("bbbbaaaaccccdddd")
                .setReceiverNewBalance(1010)
                .setSenderNewBalance(900)
                .setAmount(10)
                .setMessage("-")
                .build();

        grpcServerImp.transfer(transferRequest, responseObserver);

        Mockito.verify(transactionDao).makeTransfer(updatedBalancesForTransferDTO.capture());
        Mockito.verify(responseObserver).onNext(Mockito.any());
        Mockito.verify(responseObserver).onCompleted();
        assertEquals("aaaabbbbccccdddd", updatedBalancesForTransferDTO.getValue().getSenderId());
        assertEquals("bbbbaaaaccccdddd", updatedBalancesForTransferDTO.getValue().getReceiverId());
        assertEquals(1010, updatedBalancesForTransferDTO.getValue().getNewReceiverBalance());
        assertEquals(900, updatedBalancesForTransferDTO.getValue().getNewSenderBalance());
        assertEquals(10, updatedBalancesForTransferDTO.getValue().getAmount());
        assertEquals("-", updatedBalancesForTransferDTO.getValue().getMessage());
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

        Mockito.when(transactionDao.checkAccountId(Mockito.any())).thenReturn("bbbbaaaaccccdddd");

        grpcServerImp.checkAccount(accountCheckRequest, responseObserver);
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
    void checkInterestRate_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<InterestRateCheckResponse> responseObserver = Mockito.mock(StreamObserver.class);
        InterestRateCheckRequest request = InterestRateCheckRequest.newBuilder()
                .setAccountId("bbbbaaaaccccdddd")
                .build();
        grpcServerImp.checkInterestRate(request, responseObserver);

        Mockito.verify(transactionDao).checkInterestRate(accountCheckCaptor.capture());
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
        CreditInterestRequest request = CreditInterestRequest.newBuilder()
                .setAccountNumber("aaaabbbbccccdddd")
                .setAmount(100)
                .setBalance(22000)
                .build();
        grpcServerImp.creditInterest(request, response);

        Mockito.verify(transactionDao).creditInterest(creditInterestDTO.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals("aaaabbbbccccdddd", creditInterestDTO.getValue().getAccount_id());
        assertEquals(100, creditInterestDTO.getValue().getAmount());
        assertEquals(22000, creditInterestDTO.getValue().getNewBalance());
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

    @Test
    void logLoan_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<LogLoanResponse> response = Mockito.mock(StreamObserver.class);
        java.sql.Timestamp sqlTimestamp1 = java.sql.Timestamp.valueOf(LocalDateTime.now().minusDays(1));
        com.google.protobuf.Timestamp date1 = com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(sqlTimestamp1.getTime() / 1000)
                .setNanos((int) ((sqlTimestamp1.getTime() % 1000) * 1_000_000))
                .build();
        LogLoanRequest request = LogLoanRequest.newBuilder().setLoanAmount(1000).setAccountId("1")
                .setRemainingAmount(500).setInterestRate(7).setMonthlyPayment(200).setEndDate(date1).build();
        grpcServerImp.logLoan(request,response);

        Mockito.verify(transactionDao).logLoan(loanRequestDto.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals(1000,loanRequestDto.getValue().getLoanAmount());
        assertEquals("1",loanRequestDto.getValue().getAccountId());
        assertEquals(500,loanRequestDto.getValue().getRemainingAmount());
        assertEquals(7,loanRequestDto.getValue().getInterestRate());
        assertEquals(200,loanRequestDto.getValue().getMonthlyPayment());
        assertEquals(date1,loanRequestDto.getValue().getEndDate());
    }

    @Test
    void getTransaction_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<GetTransactionsResponse> response = Mockito.mock(StreamObserver.class);
        GetTransactionsRequest request = GetTransactionsRequest.newBuilder().setEmail("email@email.com").build();
        grpcServerImp.getTransactions(request,response);

        Mockito.verify(transactionDao).getAllTransactions(userInfoEmailDto.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals("email@email.com",userInfoEmailDto.getValue().getEmail());
    }

    @Test
    void registerUser_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<RegisterResponse> response = Mockito.mock(StreamObserver.class);
        RegisterRequest request = RegisterRequest.newBuilder().setEmail("email@emial.com").setFirstname("vlad")
                .setMiddlename("").setLastname("Nita").setPassword("123123123").setPlan("premium").build();
        grpcServerImp.registerUser(request,response);

        Mockito.verify(registerDao).registerUser(registerRequestDto.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals("email@emial.com",registerRequestDto.getValue().getEmail());
        assertEquals("123123123",registerRequestDto.getValue().getPassword());
        assertEquals("vlad",registerRequestDto.getValue().getFirstname());
        assertEquals("",registerRequestDto.getValue().getMiddlename());
        assertEquals("Nita",registerRequestDto.getValue().getLastname());
    }

    @Test
    void getUserByEmail_calls_dao_sends_response() throws SQLException {
        StreamObserver<UserEmailResponse> response = Mockito.mock(StreamObserver.class);
        UserEmailRequest request = UserEmailRequest.newBuilder().setEmail("email@email.com").build();

        Mockito.when(registerDao.getUserEmail(Mockito.any())).thenReturn("email@email.colm");

        grpcServerImp.getUserByEmail(request, response);

        Mockito.verify(registerDao).getUserEmail(userAccountRequestDto.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals("email@email.com", userAccountRequestDto.getValue().getEmail());
    }

    @Test
    void getUserId_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<UserAccountResponse> response = Mockito.mock(StreamObserver.class);
        UserAccountRequest request = UserAccountRequest.newBuilder().setEmail("levi@gmail.com").build();
        grpcServerImp.getUserId(request,response);

        Mockito.verify(registerDao).getUserID(userAccountRequestDto.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals("levi@gmail.com",userAccountRequestDto.getValue().getEmail());
    }

    @Test
    void createUserAccountNumber_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<AccountCreateResponse> response = Mockito.mock(StreamObserver.class);
        AccountCreateRequest request = AccountCreateRequest.newBuilder().setInterestRate(7).setUserId(1).setUserAccountNumber("12345678912345").build();
        grpcServerImp.createUserAccountNumber(request,response);

        Mockito.verify(registerDao).generateAccountNumber(userAccountDto.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals(7,userAccountDto.getValue().getInterestRate());
        assertEquals(1,userAccountDto.getValue().getUser_id());
        assertEquals("12345678912345",userAccountDto.getValue().getUserAccountNumber());
    }

    /*@Test
    void changeUserDetails_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<UserNewDetailsResponse> response = Mockito.mock(StreamObserver.class);
        UserNewDetailsRequest request = UserNewDetailsRequest.newBuilder().setNewEmail("123@gmail.com").setPlan("premium")
                .setOldEmail("asd@gmail.com").setPassword("12345678").build();
        grpcServerImp.changeUserDetails(request,response);

        Mockito.verify(credentialChangerDao).
    }*/

    @Test
    void changeBaseRate_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<AccountNewBaseRateResponse> response = Mockito.mock(StreamObserver.class);
        AccountNewBaseRateRequest request = AccountNewBaseRateRequest.newBuilder().setBaseRate(10.2).setUserId(1).build();
        grpcServerImp.changeBaseRate(request,response);

        Mockito.verify(credentialChangerDao).UpdateBaseRate(accountNewBaseRateDto.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals(10.2,accountNewBaseRateDto.getValue().getBaseRate());
        assertEquals(1,accountNewBaseRateDto.getValue().getUser_id());
    }

    @Test
    void createIssue_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<CreateIssueResponse> response = Mockito.mock(StreamObserver.class);
        CreateIssueRequest request = CreateIssueRequest.newBuilder().setTitle("title").setBody("body").setOwner(1).build();
        grpcServerImp.createIssue(request,response);

        Mockito.verify(chatDao).createIssue(issueCreationDto.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals("title",issueCreationDto.getValue().getTitle());
        assertEquals("body",issueCreationDto.getValue().getBody());
        assertEquals(1,issueCreationDto.getValue().getOwnerId());
    }

    @Test
    void sendMessage_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<SendMessageResponse> response = Mockito.mock(StreamObserver.class);
        SendMessageRequest request = SendMessageRequest.newBuilder().setOwner(1).setBody("body").setTitle("title").setIssueId(1).build();
        grpcServerImp.sendMessage(request,response);

        Mockito.verify(chatDao).sendMessage(messageDto.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals(1,messageDto.getValue().getOwner());
        assertEquals("body",messageDto.getValue().getBody());
        assertEquals("title",messageDto.getValue().getTitle());
        assertEquals(1,messageDto.getValue().getIssueId());
    }

    @Test
    void getAllIssues_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<GetAllIssuesResponse> response = Mockito.mock(StreamObserver.class);
        GetAllIssuesRequest request = GetAllIssuesRequest.newBuilder().build();
        grpcServerImp.getAllIssues(request,response);

        Mockito.verify(chatDao).getAllIssues();
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
    }

    @Test
    void getMessagesByIssueId_calls_dao_send_response() throws SQLException {
        StreamObserver<GetMessagesByIssueIdResponse> response = Mockito.mock(StreamObserver.class);
        GetMessagesByIssueIdRequest request = GetMessagesByIssueIdRequest.newBuilder().setIssueId(1).build();
        grpcServerImp.getMessagesByIssueId(request,response);

        Mockito.verify(chatDao).getMessagesByIssueId(issueinfoDto.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals(1,issueinfoDto.getValue().getId());
    }

    @Test
    void getMessagesForIssue_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<GetMessagesForIssueResponse> response = Mockito.mock(StreamObserver.class);
        GetMessagesForIssueRequest request = GetMessagesForIssueRequest.newBuilder().setIssueId(1).build();
        grpcServerImp.getMessagesForIssue(request,response);

        Mockito.verify(chatDao).getMessagesForIssue(issueinfoDto.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals(1,issueinfoDto.getValue().getId());
    }

    @Test
    void getSubscriptions_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<GetTransactionsResponse> response = Mockito.mock(StreamObserver.class);
        GetTransactionsRequest request = GetTransactionsRequest.newBuilder().setEmail("-").build();
        grpcServerImp.getSubscriptions(request,response);

        Mockito.verify(transactionDao).getAllSubscriptions(userInfoEmailDto.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals("-",userInfoEmailDto.getValue().getEmail());
    }

    @Test
    void changeUserDetails_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<UserNewDetailsResponse> response = Mockito.mock(StreamObserver.class);
        UserNewDetailsRequest request = UserNewDetailsRequest.newBuilder().setNewEmail("-").setOldEmail(".").setPlan("=").setPassword("-").build();
        grpcServerImp.changeUserDetails(request,response);

        Mockito.verify(credentialChangerDao).UpdateUserInformation(userNewDetailsRequestDTO.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals("-",userNewDetailsRequestDTO.getValue().getNewEmail());
        assertEquals(".",userNewDetailsRequestDTO.getValue().getOldEmail());
        assertEquals("=",userNewDetailsRequestDTO.getValue().getPlan());
        assertEquals("-",userNewDetailsRequestDTO.getValue().getPassword());
    }

    @Test
    void updateIssue_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<UpdateIssueResponse> response = Mockito.mock(StreamObserver.class);
        UpdateIssueRequest request = UpdateIssueRequest.newBuilder().setId(1).build();
        grpcServerImp.updateIssue(request,response);

        Mockito.verify(chatDao).updateIssue(issueUpdateDTO.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals(1,issueUpdateDTO.getValue().getId());
    }

    @Test
    void updateEmail_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<UserNewEmailResponse> response = Mockito.mock(StreamObserver.class);
        UserNewEmailRequest request = UserNewEmailRequest.newBuilder().setUserId(1).setEmail("-").build();
        grpcServerImp.updateEmail(request,response);

        Mockito.verify(credentialChangerDao).UpdateEmail(userNewEmailDTO.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals(1,userNewEmailDTO.getValue().getUserID());
        assertEquals("-",userNewEmailDTO.getValue().getEmail());
    }
    @Test
    void updatePassword_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<UserNewPasswordResponse> response = Mockito.mock(StreamObserver.class);
        UserNewPasswordRequest request = UserNewPasswordRequest.newBuilder().setUserId(1).setPassword("-").build();
        grpcServerImp.updatePassword(request,response);

        Mockito.verify(credentialChangerDao).UpdatePassword(userNewPasswordDTO.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals(1,userNewPasswordDTO.getValue().getUserID());
        assertEquals("-",userNewPasswordDTO.getValue().getPassword());
    }
    @Test
    void updatePlan_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<UserNewPlanResponse> response = Mockito.mock(StreamObserver.class);
        UserNewPlanRequest request = UserNewPlanRequest.newBuilder().setUserId(1).setPlan("-").build();
        grpcServerImp.updatePlan(request,response);

        Mockito.verify(credentialChangerDao).UpdatePlan(userNewPlanDTO.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals(1,userNewPlanDTO.getValue().getUserID());
        assertEquals("-",userNewPlanDTO.getValue().getPlan());
    }
    @Test
    void flagUser_calls_dao_and_sends_response() throws SQLException {
        StreamObserver<FlagUserResponse> response = Mockito.mock(StreamObserver.class);
        FlagUserRequest request = FlagUserRequest.newBuilder().setSenderId(1).build();
        grpcServerImp.flagUser(request,response);

        Mockito.verify(transactionDao).flagUser(flagUserDTO.capture());
        Mockito.verify(response).onNext(Mockito.any());
        Mockito.verify(response).onCompleted();
        assertEquals(1,flagUserDTO.getValue().getSenderId());
    }
}
