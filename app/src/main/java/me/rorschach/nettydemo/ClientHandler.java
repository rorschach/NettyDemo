package me.rorschach.nettydemo;

import android.util.Log;
import hugo.weaving.DebugLog;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by lei on 16-6-8.
 */
@ChannelHandler.Sharable public class ClientHandler extends SimpleChannelInboundHandler<Object> {

    private static final String TAG = "ClientHandler";

    //private Channel mChannel;

    @DebugLog @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Log.d(TAG, "channelActive");
    }

    @DebugLog @Override public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        Log.e(TAG, "channelInactive");
    }

    @Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        Log.d(TAG, "channelRead: " + msg.toString());
    }

    @Override public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        Log.d(TAG, "channelReadComplete: ");
    }

    @DebugLog @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o)
            throws Exception {
        Log.e(TAG, "read: " + o.toString());
    }

    @DebugLog public void write(Channel channel, String msg) {
        if (channel == null) {
            Log.e(TAG, "write but the channel is null!!");
            return;
        }

        channel.writeAndFlush(Unpooled.copiedBuffer(msg.getBytes()));
    }
}
