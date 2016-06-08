package me.rorschach.nettydemo;

import android.util.Log;
import hugo.weaving.DebugLog;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by lei on 16-6-8.
 */
public class Client {

    private static final String TAG = "Client";

    private static Bootstrap sBootstrap;

    private static Channel sChannel;

    private static final ClientHandler sHandler = new ClientHandler();

    @DebugLog public static void start() {
        init();
        connect();
    }

    @DebugLog public static void init() {
        sBootstrap = configureBootstrap(new Bootstrap(), new NioEventLoopGroup());
    }

    @DebugLog public static Bootstrap configureBootstrap(Bootstrap b, EventLoopGroup g) {
        b.group(g)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .remoteAddress(Constants.HOST, Constants.PORT)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override public void initChannel(SocketChannel ch) throws Exception {

                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));

                        pipeline.addLast(new IdleStateHandler(Constants.HEARD_BEAT_DELAY, 0, 0),
                                sHandler);
                    }
                });

        return b;
    }

    @DebugLog public static void connect() {

        if (sBootstrap == null) {
            Log.e(TAG, "connect with null bootstrap!!!");
            return;
        }

        sBootstrap.connect().addListener(new ChannelFutureListener() {
            @Override public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    sChannel = channelFuture.channel();
                } else {
                    Log.e(TAG, channelFuture.toString());
                }
            }
        });
    }

    @DebugLog public static void disconnect() {
        if (sChannel != null) {
            sChannel.disconnect();
        }else {
            Log.e(TAG, "disconnect");
        }
    }

    public static void send(String msg) {
        sHandler.write(sChannel, msg);
    }
}
