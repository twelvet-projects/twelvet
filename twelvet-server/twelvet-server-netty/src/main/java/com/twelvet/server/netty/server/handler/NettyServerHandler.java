package com.twelvet.server.netty.server.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: Netty服务端自定义事件处理
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private final static Logger log = LoggerFactory.getLogger(NettyServerHandler.class);

    /**
     * 读取客户端发送数据
     *
     * @param ctx ChannelHandlerContext
     * @param msg Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //处理websocket客户端的消息
        handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
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
        ctx.close();
    }

    /**
     * 处理业务
     *
     * @param ctx   ChannelHandlerContext
     * @param frame WebSocketFrame
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel()
                    .write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 本不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            log.debug("本例程仅支持文本消息，不支持二进制消息");
            throw new UnsupportedOperationException(String.format(
                    "%s frame types not supported", frame.getClass().getName()));
        }
        // 返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        log.debug("服务端收到：" + request);
        TextWebSocketFrame tws = new TextWebSocketFrame(new Date().toString()
                + ctx.channel().id() + "：" + request);
        // 返回【谁发的发给谁】
        ctx.channel().writeAndFlush(tws);
    }

}
