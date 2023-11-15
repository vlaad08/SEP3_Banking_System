package Database.Server;

import Database.*;
import Database.DAOs.TransactionDao;
import Database.DTOs.CheckAccountIdDTO;
import Database.DTOs.TransferRequestDTO;
import io.grpc.stub.StreamObserver;

import java.sql.SQLException;


public class GRPCServerImp extends DatabaseServiceGrpc.DatabaseServiceImplBase {
    TransactionDao transactionDao = new TransactionDao();
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
        CheckAccountIdDTO checkBalanceDTO = new CheckAccountIdDTO(request.getRecipientAccountId());
        String recipientAccount_id;
        try {
            recipientAccount_id = transactionDao.checkAccountId(checkBalanceDTO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        AccountCheckResponse response = AccountCheckResponse.newBuilder().setRecipientAccountId(recipientAccount_id).build();
        System.out.println(response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
