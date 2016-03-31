package client;

import game.thrift.UserStorage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by yangzhao on 3/28/2016.
 */
public class GameClient {
    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws Exception {
        TTransport transport = new TFramedTransport(new TSocket("127.0.0.1", 7911));

        transport.open();
        System.out.println(transport.isOpen());
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol tmp = new TMultiplexedProtocol(protocol,"GameService");
        UserStorage.Client client = new UserStorage.Client(tmp);

        long start = System.currentTimeMillis();

        for(int i=0;i<1;i++){
            System.out.println("Client call");
            long id = client.generateSessionId();
            System.out.println(id);
        }

        System.out.println("total used :"+(System.currentTimeMillis() - start));

        transport.close();
    }
}
