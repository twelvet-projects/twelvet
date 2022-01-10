package com.twelvet.server.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: Netty客户端自定义事件处理
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private final static Logger log = LoggerFactory.getLogger(NettyClientHandler.class);

    /**
     * 读取服务端发送的信息
     *
     * @param ctx ChannelHandlerContext
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server", CharsetUtil.UTF_8));
    }

    /**
     * 通道有读取事件时，会触发
     *
     * @param ctx ChannelHandlerContext
     * @param msg Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        log.info("服务器回复的消息：{}", buf.toString(CharsetUtil.UTF_8));
        log.info("服务器地址：{}", ctx.channel().remoteAddress());
    }

    /**
     * 捕获异常
     *
     * @param ctx   ChannelHandlerContext
     * @param cause Throwable
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("netty服务链接异常：", cause);
        ctx.close();
    }
}
