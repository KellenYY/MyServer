package server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by yang on 2016/3/24.
 */
public class ServerHandler extends ChannelHandlerAdapter {
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception{
        System.out.println("handlerAdded");
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved");
    }
}
