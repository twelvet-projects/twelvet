package com.twelvet.server.netty.server.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author twelvet
 * <p>
 * 自定义处理器
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 初始化设置
     *
     * @param socketChannel SocketChannel
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline()
                // http解码器
                .addLast(new HttpServerCodec())
                // 自定义处理事件
                .addLast(new SocketServerHandler())
                // 支持写大数据流
                .addLast(new ChunkedWriteHandler())
                // http聚合器
                .addLast(new HttpObjectAggregator(1024 * 62))
                //ws://localhost:8989/ws
                .addLast(new WebSocketServerProtocolHandler("/socket"));
    }

}
