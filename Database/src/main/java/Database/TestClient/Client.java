package Database.TestClient;

import Database.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Client {
    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost",9090).usePlaintext().build();
        DatabaseServiceGrpc.DatabaseServiceBlockingStub databaseStub = DatabaseServiceGrpc.newBlockingStub(managedChannel);

        AccountCheckRequest request = AccountCheckRequest.newBuilder()
                .setRecipientAccountId("sdfas")
                .build();
        AccountCheckResponse response = databaseStub.checkAccount(request);
        System.out.println(response.getRecipientAccountId());

        BalanceCheckRequest request1 = BalanceCheckRequest.newBuilder()
                .setAccountId("aaaabbbbccccdddd")
                .build();
        BalanceCheckResponse response1 = databaseStub.checkBalance(request1);
        System.out.println(response1.getBalance());
    }
}