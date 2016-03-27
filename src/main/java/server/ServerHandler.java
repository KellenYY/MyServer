package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by yang on 2016/3/24.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("active");
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int)(System.currentTimeMillis()));
        final ChannelFuture f = ctx.writeAndFlush(time);
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("inactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        /*
        ByteBuf in = (ByteBuf)msg;
        try{
            System.out.println(in.toString(CharsetUtil.US_ASCII));
        }finally{
            ReferenceCountUtil.release(msg);
        }
        */
        ctx.writeAndFlush(msg);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
        System.out.println("exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }
}
