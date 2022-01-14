package com.twelvet.server.netty.server.handler;

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
 * @Description: Netty服务端自定义事件处理
 */
public class WebSocketServerHandler extends ChannelInboundHandlerAdapter {

    private final static Logger log = LoggerFactory.getLogger(WebSocketServerHandler.class);

    /**
     * 读取客户端发送数据
     *
     * @param ctx ChannelHandlerContext
     * @param msg Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        log.info("客户端发送的消息是：{}", buf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 数据读取完毕后执行
     *
     * @param ctx ChannelHandlerContext
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("收到信息，服务器返回信息");
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端", CharsetUtil.UTF_8));
    }

    /**
     * 异常信息关闭
     *
     * @param ctx   ChannelHandlerContext
     * @param cause Throwable
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("netty服务链接异常：", cause);
        cause.printStackTrace();
        ctx.close();
    }
}
