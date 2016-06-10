package me.rorschach.nettydemo;

import android.util.Log;
import hugo.weaving.DebugLog;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import java.util.concurrent.TimeUnit;

/**
 * Created by lei on 16-6-8.
 */
@ChannelHandler.Sharable public class ClientHandler extends SimpleChannelInboundHandler<Object> {

    private static final String TAG = "ClientHandler";

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o)
            throws Exception {
        Log.e(TAG, "read: " + o.toString());
        Client.receive(o.toString());
    }

    @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Log.d(TAG, "channelActive");

        RetryHandler.resetRetryCounts();
        write(ctx.channel(), "{\"userId\":500,\"messageType\":\"Login\"}");
    }

    @Override public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        Log.e(TAG, "channelInactive");
    }

    /**
     * sent heartBeat
     * @param ctx
     * @param evt
     */
    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, Object evt) {

        if (!Client.isStartConnect()) {
            Log.e(TAG, "client is not start connect!");
            return;
        }

        if (!(evt instanceof IdleStateEvent)) {
            return;
        }

        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override public void run() {
                Log.d(TAG, "sent heartBeat...");
                write(ctx.channel(), "{\"userId\":500,\"messageType\":\"Ping\"}");
            }
        }, Constants.HEARD_BEAT_DELAY, TimeUnit.SECONDS);
    }

    /**
     * reconnect
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {

        if (!Client.isStartConnect()) {
            Log.e(TAG, "client is not start connect!");
            return;
        }

        long waitSecond = RetryHandler.calculateRetryCounts();
        Log.d(TAG, "wait: " + waitSecond + " s to reconnect...");

        final EventLoop loop = ctx.channel().eventLoop();
        loop.schedule(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "start reconnect...");
               Client.start();
            }
        }, waitSecond, TimeUnit.SECONDS);
    }

    @DebugLog public void write(Channel channel, String msg) {
        if (channel == null) {
            Log.e(TAG, "write but the channel is null!!");
            return;
        }

        channel.writeAndFlush(Unpooled.copiedBuffer(msg.getBytes()));
    }
}
