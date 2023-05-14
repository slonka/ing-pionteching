package net.slonka.greencode;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    // 100k transactions are 17MBs in size
    private final static int MAX_CONTENT_LENGTH = 50 * 1024 * 1024; // 50MB

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast("encoder", new HttpResponseEncoder() {
                    @Override
                    public boolean acceptOutboundMessage(final Object msg) throws Exception {
                        if (msg.getClass() == DefaultFullHttpResponse.class) {
                            return true;
                        }
                        return super.acceptOutboundMessage(msg);
                    }
                })
                .addLast("decoder", new HttpRequestDecoder())
                .addLast("aggregator", new HttpObjectAggregator(MAX_CONTENT_LENGTH))
                .addLast("httpHandler", new ServiceHandler());
    }
}