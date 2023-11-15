package Database.Server;

import Database.*;
import Database.DAOs.TransactionDao;
import Database.DTOs.TransferRequestDTO;
import io.grpc.stub.StreamObserver;


public class GRPCServerImp extends DatabaseServiceGrpc.DatabaseServiceImplBase {
    TransactionDao transactionDao = new TransactionDao();
    @Override
    public void transfer(TransferRequest request, StreamObserver<TransferResponse> responseObserver) {
        String resp = "Transfer happened";
        System.out.println("FFFFFFFFFFFFFFF");
        TransferRequestDTO transferRequestDTO = new TransferRequestDTO(request.getSenderAccountId(), request.getRecipientAccountId(),request.getBalance(),request.getMessage());
        transactionDao.makeTransfer(transferRequestDTO);
        TransferResponse response = TransferResponse.newBuilder().setResp(resp).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void checkBalance(BalanceCheckRequest request, StreamObserver<BalanceCheckResponse> responseObserver)
    {

    }

}
