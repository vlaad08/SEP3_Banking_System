package Database.Server;

import Database.DatabaseServiceGrpc;
import Database.TransferRequest;
import Database.TransferResponse;
import io.grpc.stub.StreamObserver;


public class GRPCServerImp extends DatabaseServiceGrpc.DatabaseServiceImplBase {
    @Override
    public void transfer(TransferRequest request, StreamObserver<TransferResponse> responseObserver) {
        String resp = "Transfer happened";
        TransferResponse response = TransferResponse.newBuilder().setResp(resp).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
