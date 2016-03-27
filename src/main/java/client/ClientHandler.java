package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by yangzhao on 3/28/2016.
 */
public class ClientHandler extends SimpleChannelInboundHandler<Object> {

    private ByteBuf content;
    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        this.ctx = ctx;
        content = ctx.alloc().directBuffer(10).writeZero(10);

        generateTraffic();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        content.release();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }

    long counter;

    private void generateTraffic(){
        ctx.writeAndFlush(content.duplicate().retain()).addListener(trafficGenerator);
    }

    private  final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            if ((channelFuture.isSuccess())){
                generateTraffic();
            }else{
                channelFuture.cause().printStackTrace();
                channelFuture.channel().close();
            }
        }
    };

}
