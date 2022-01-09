package com.twelvet.server.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description:
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx =" + ctx);
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是"+ buf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 数据读取完毕后执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("收到信息，服务器返回信息");
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
