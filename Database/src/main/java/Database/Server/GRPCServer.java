package Database.Server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GRPCServer {
    public static void main( String[] args )
            throws Exception
    {
        Server server = ServerBuilder
                .forPort(9090)
                .addService(new GRPCServerImp())
                .build();

        server.start();
        server.awaitTermination();
    }
}
