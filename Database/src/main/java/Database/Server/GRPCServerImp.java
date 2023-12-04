package Database.Server;

import Database.*;
import Database.AccountCheckRequest;
import Database.AccountCheckResponse;
import Database.AccountsInfo;
import Database.AllAccountsInfoRequest;
import Database.AllAccountsInfoResponse;
import Database.BalanceCheckRequest;
import Database.BalanceCheckResponse;
import Database.CreditInterestRequest;
import Database.CreditInterestResponse;
import Database.DAOs.CredentialChangerDao;
import Database.DAOs.Interfaces.LoginDaoInterface;
import Database.DAOs.Interfaces.TransactionDaoInterface;
import Database.DAOs.LoginDao;
import Database.DAOs.RegisterDao;
import Database.DAOs.TransactionDao;
import Database.DTOs.*;
import Database.DailyCheckRequest;
import Database.DailyCheckResponse;
import Database.DatabaseServiceGrpc;
import Database.DepositRequest;
import Database.DepositResponse;
import Database.GetTransactionsRequest;
import Database.GetTransactionsResponse;
import Database.LastInterestRequest;
import Database.LastInterestResponse;
import Database.LogLoanRequest;
import Database.LogLoanResponse;
import Database.LoginValidationRequest;
import Database.LoginValidationResponse;
import Database.Transactions;
import Database.TransferRequest;
import Database.TransferResponse;
import Database.User;
import Database.UserAccountInfoRequest;
import Database.UserAccountInfoResponse;
import io.grpc.stub.StreamObserver;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;


public class GRPCServerImp extends DatabaseServiceGrpc.DatabaseServiceImplBase {
    TransactionDaoInterface transactionDao = new TransactionDao();
    LoginDaoInterface loginDao = new LoginDao();
    RegisterDao registerDao = new RegisterDao();
    CredentialChangerDao credentialChangerDao = new CredentialChangerDao();
    @Override
    public void transfer(TransferRequest request, StreamObserver<TransferResponse> responseObserver) {
        System.out.println("TRANSFER");
        TransferRequestDTO transferRequestDTO = new TransferRequestDTO(request.getSenderAccountId(), request.getRecipientAccountId(),request.getBalance(),request.getMessage());
        transactionDao.makeTransfer(transferRequestDTO);
        String resp = "Transfer happened";
        TransferResponse response = TransferResponse.newBuilder().setResp(resp).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void checkAccount(AccountCheckRequest request, StreamObserver<AccountCheckResponse> responseObserver) {
        CheckAccountDTO checkAccountDTO = new CheckAccountDTO(request.getRecipientAccountId());
        String recipientAccount_id;
        try {
            recipientAccount_id = transactionDao.checkAccountId(checkAccountDTO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        AccountCheckResponse response = AccountCheckResponse.newBuilder().setRecipientAccountId(recipientAccount_id).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void checkBalance(BalanceCheckRequest request, StreamObserver<BalanceCheckResponse> responseObserver)
    {
        CheckAccountDTO checkBalanceDTO = new CheckAccountDTO(request.getAccountId());
        double balance;
        try {
            balance = transactionDao.checkBalance(checkBalanceDTO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        BalanceCheckResponse response = BalanceCheckResponse.newBuilder().setBalance(balance).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void dailyCheckTransactions(DailyCheckRequest request, StreamObserver<DailyCheckResponse> responseObserver)
    {
        CheckAccountDTO checkAccountDTO = new CheckAccountDTO(request.getAccountId());
        double amount = 0;
        try
        {
            amount = transactionDao.dailyCheck(checkAccountDTO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DailyCheckResponse response = DailyCheckResponse.newBuilder().setAmount(amount).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deposit(DepositRequest request, StreamObserver<DepositResponse> responseObserver) {
        try
        {
            DepositRequestDTO depositRequestDTO = new DepositRequestDTO(request.getAccountId(), request.getAmount());
            transactionDao.makeDeposit(depositRequestDTO);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DepositResponse response = DepositResponse.newBuilder().setResp("Deposit happened").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    @Override
    public void loginValidation(LoginValidationRequest request, StreamObserver<LoginValidationResponse> responseObserver) {
        try
        {
            List<User> users = loginDao.getUsers();
            LoginValidationResponse response = LoginValidationResponse.newBuilder().addAllUsers(users).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void allAccountsInfo(AllAccountsInfoRequest request, StreamObserver<AllAccountsInfoResponse> responseStreamObserver)
    {
        try
        {
            List<AccountsInfo> accInfo = loginDao.getAccountsInfo();
            AllAccountsInfoResponse response = AllAccountsInfoResponse.newBuilder().addAllAccountInfo(accInfo).build();
            responseStreamObserver.onNext(response);
            responseStreamObserver.onCompleted();
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void userAccountsInfo(UserAccountInfoRequest request, StreamObserver<UserAccountInfoResponse> responseStreamObserver)
    {
        try{
            UserInfoEmailDTO userInfoDTO = new UserInfoEmailDTO(request.getEmail());
            List<AccountsInfo> accInfo = loginDao.getUserAccountInfos(userInfoDTO);
            UserAccountInfoResponse response = UserAccountInfoResponse.newBuilder().addAllAccountInfo(accInfo).build();
            responseStreamObserver.onNext(response);
            responseStreamObserver.onCompleted();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void creditInterest(CreditInterestRequest request, StreamObserver<CreditInterestResponse> responseStreamObserver)
    {
        try{
            UserInfoAccNumDTO userInfoDTO = new UserInfoAccNumDTO(request.getAccountNumber());
            boolean happened = transactionDao.creditInterest(userInfoDTO);
            CreditInterestResponse response = CreditInterestResponse.newBuilder().setHappened(happened).build();
            responseStreamObserver.onNext(response);
            responseStreamObserver.onCompleted();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void lastInterest(LastInterestRequest request, StreamObserver<LastInterestResponse> responseStreamObserver) {
        try {
            UserInfoAccNumDTO userInfoDTO = new UserInfoAccNumDTO(request.getAccountNumber());
            Timestamp date = transactionDao.lastInterest(userInfoDTO);
            LastInterestResponse response;
            if(date != null) {
                // Convert Java Timestamp to Google's Timestamp
                com.google.protobuf.Timestamp timestampProto = com.google.protobuf.Timestamp.newBuilder()
                        .setSeconds(date.getTime() / 1000)  // Convert milliseconds to seconds
                        .setNanos((int) ((date.getTime() % 1000) * 1000000))  // Convert remaining milliseconds to nanoseconds
                        .build();

                response = LastInterestResponse.newBuilder().setDate(timestampProto).build();
            }
            else {
                response = null;
            }
            responseStreamObserver.onNext(response);
            responseStreamObserver.onCompleted();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void logLoan(LogLoanRequest request, StreamObserver<LogLoanResponse> responseStreamObserver) {
        try {
            LoanRequestDTO loanRequestDTO = new LoanRequestDTO(request.getAccountId(),request.getRemainingAmount(),
                    request.getInterestRate(),request.getMonthlyPayment(),request.getEndDate(),request.getLoanAmount());
            transactionDao.logLoan(loanRequestDTO);
            LogLoanResponse response = LogLoanResponse.newBuilder().setResponse("Loan granted").build();
            responseStreamObserver.onNext(response);
            responseStreamObserver.onCompleted();
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getTransactions(GetTransactionsRequest request, StreamObserver<GetTransactionsResponse> responseStreamObserver) {
        try {
            UserInfoEmailDTO userInfoEmailDTO = new UserInfoEmailDTO(request.getEmail());
            List<Transactions> transactions = transactionDao.getAllTransactions(userInfoEmailDTO);
            GetTransactionsResponse response = GetTransactionsResponse.newBuilder().addAllTransactions(transactions).build();
            responseStreamObserver.onNext(response);
            responseStreamObserver.onCompleted();
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void registerUser(RegisterRequest request, StreamObserver<RegisterResponse> responseStreamObserver)
    {
        try
        {
            RegisterRequestDTO registerUserDTO = new RegisterRequestDTO(
                request.getEmail(), request.getFirstname(),
                request.getMiddlename(), request.getLastname(),
                request.getPassword(), request.getPlan()
            );
            registerDao.registerUser(registerUserDTO);
            RegisterResponse response = RegisterResponse.newBuilder().build();
            responseStreamObserver.onNext(response);
            responseStreamObserver.onCompleted();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getUserByEmail(UserEmailRequest request, StreamObserver<UserEmailResponse> responseStreamObserver)
    {
        try
        {
            UserAccountRequestDTO userAccountRequestDTO = new UserAccountRequestDTO(request.getEmail());
            String email = registerDao.getUserEmail(userAccountRequestDTO);
            UserEmailResponse response = UserEmailResponse.newBuilder().setEmail(email).build();
            responseStreamObserver.onNext(response);
            responseStreamObserver.onCompleted();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getUserId(UserAccountRequest request, StreamObserver<UserAccountResponse> responseStreamObserver)
    {
        try
        {
            UserAccountRequestDTO userAccountRequestDTO = new UserAccountRequestDTO(request.getEmail());
            int user_id = registerDao.getUserID(userAccountRequestDTO);
            UserAccountResponse response = UserAccountResponse.newBuilder().setUserId(
                String.valueOf(user_id)).build();
            responseStreamObserver.onNext(response);
            responseStreamObserver.onCompleted();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUserAccountNumber(AccountCreateRequest request, StreamObserver<AccountCreateResponse> responseStreamObserver)
    {
        try
        {
            UserAccountDTO userAccountDTO = new UserAccountDTO(
                Integer.parseInt(request.getUserId()),request.getUserAccountNumber(), request.getAccountType(), Double.parseDouble(request.getInterestRate())
            );
            registerDao.generateAccountNumber(userAccountDTO);
            AccountCreateResponse response = AccountCreateResponse.newBuilder().build();
            responseStreamObserver.onNext(response);
            responseStreamObserver.onCompleted();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeUserDetails(UserNewDetailsRequest request, StreamObserver<UserNewDetailsResponse> responseStreamObserver)
    {
        try
        {
            UserNewDetailsRequestDTO userNewDetailsRequestDTO = new UserNewDetailsRequestDTO(
                request.getNewEmail(), request.getOldEmail(), request.getPassword(), request.getPlan()
            );
            credentialChangerDao.UpdateUserInformation(userNewDetailsRequestDTO);
            UserNewDetailsResponse response = UserNewDetailsResponse.newBuilder().build();
            responseStreamObserver.onNext(response);
            responseStreamObserver.onCompleted();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeBaseRate(AccountNewBaseRateRequest request, StreamObserver<AccountNewBaseRateResponse> responseStreamObserver)
    {
        try
        {
            AccountNewBaseRateDTO accountNewBaseRateDTO = new AccountNewBaseRateDTO(
                Integer.parseInt(request.getUserId()), Double.parseDouble(request.getBaseRate())
            );
            credentialChangerDao.UpdateNewBaseRate(accountNewBaseRateDTO);
            AccountNewBaseRateResponse response = AccountNewBaseRateResponse.newBuilder().build();
            responseStreamObserver.onNext(response);
            responseStreamObserver.onCompleted();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


}
