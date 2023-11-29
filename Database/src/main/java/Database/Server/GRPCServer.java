package Database.Server;

import Database.DatabaseServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class GRPCServer {
    public static void main( String[] args )
            throws Exception
    {
        Server server ;
        SocketAddress address = new InetSocketAddress("localhost",9090);
        server = NettyServerBuilder.forAddress(address).addService(new GRPCServerImp()).build();
        server.start();
        System.out.println("The server is listening on: "+server.toString());
        server.awaitTermination();
    }
}
