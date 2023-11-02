package Database.TestClient;

import Database.DatabaseServiceGrpc;
import Database.TransferRequest;
import Database.TransferResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Client {
    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost",9090).usePlaintext().build();
        DatabaseServiceGrpc.DatabaseServiceBlockingStub databaseStub = DatabaseServiceGrpc.newBlockingStub(managedChannel);
        TransferRequest transferRequest = TransferRequest.newBuilder()
                .setSenderAccountId("aaaabbbbccccdddd")
                .setRecipientAccountId("aaaabbbbddddcccc")
                .setBalance(200)
                .setMessage("-")
                .build();
        TransferResponse transferResponse = databaseStub.transfer(transferRequest);
        System.out.println(transferResponse.getResp());
    }
}
