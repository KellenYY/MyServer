package game.server;

import game.thrift.UserStorage;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by yangzhao on 3/31/2016.
 */
public class Server {
    final Logger logger = LoggerFactory.getLogger(Server.class);
    public int threadCount=10;

    public static TServer getNonblockingServer(TProcessor processor,int port,int threadCount) throws TTransportException{
        TNonblockingServerTransport serverTransport;

        serverTransport = new TNonblockingServerSocket(port);

        TFramedTransport.Factory transportFactory = new TFramedTransport.Factory();

        TBinaryProtocol.Factory protocolFactory = new TBinaryProtocol.Factory();

        TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport)
                .transportFactory(transportFactory)
                .protocolFactory(protocolFactory)
                .processor(processor)
                .workerThreads(threadCount);

        TServer server = new TThreadedSelectorServer(tArgs);

        return server;
    }

    private void start(){
        try{
            Runnable gamer = new Runnable() {
                public void run() {
                    gameStorageService(7911);
                }
            };

            new Thread(gamer).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void gameStorageService(int port){
        try{
            TMultiplexedProcessor processor = new TMultiplexedProcessor();
            processor.registerProcessor("GameService",new UserStorage.Processor<UserStorage.Iface>(new GameServiceFacade()));
            TServer server = null;
            server = getNonblockingServer(processor,port,threadCount);
            System.out.println("Starting game server on port "+port+" ...");
            logger.info("Wait client connect!");
            server.serve();
        }catch (TTransportException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException{

        Server srv = new Server();
        srv.start();
    }
}
