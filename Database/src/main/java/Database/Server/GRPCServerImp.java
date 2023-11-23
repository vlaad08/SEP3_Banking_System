package Database.Server;

import Database.*;
import Database.DAOs.Interfaces.TransactionDaoInterface;
import Database.DAOs.TransactionDao;
import Database.DTOs.CheckAccountDTO;
import Database.DTOs.TransferRequestDTO;
import io.grpc.stub.StreamObserver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GRPCServerImp extends DatabaseServiceGrpc.DatabaseServiceImplBase {
    TransactionDaoInterface transactionDao = new TransactionDao();
    @Override
    public void transfer(TransferRequest request, StreamObserver<TransferResponse> responseObserver) {
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
            System.out.println(amount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DailyCheckResponse response = DailyCheckResponse.newBuilder().setAmount(amount).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
