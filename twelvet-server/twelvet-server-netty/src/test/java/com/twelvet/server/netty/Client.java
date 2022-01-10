package com.twelvet.server.netty;

import com.twelvet.server.netty.client.handler.NettyClientHandler;
import com.twelvet.server.netty.server.NettyServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 客户端测试用例
 */
public class Client {

    public static void main(String[] args) {
        // 客户端NIO
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            // 创建服务启动类
            Bootstrap bootstrap = new Bootstrap();
            // 设置主从线程组
            bootstrap.group(eventExecutors);

            bootstrap
                    // 使用NIO通道作为实现
                    .channel(NioSocketChannel.class)
                    // 设置处理器
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // 加入自定义处理器
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            // 开始连接服务端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8989).sync();
            // 给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭
            eventExecutors.shutdownGracefully();
        }
    }

}
