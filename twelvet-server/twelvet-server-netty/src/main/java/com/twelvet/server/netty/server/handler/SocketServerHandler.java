package com.twelvet.server.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: Netty服务端自定义事件处理
 */
public class SocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	private final static Logger log = LoggerFactory.getLogger(SocketServerHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
		// 打印接收到的消息
		log.info("客户端发送的消息是：{}", textWebSocketFrame.text());
		// 返回消息给客户端
		channelHandlerContext.writeAndFlush(
				new TextWebSocketFrame("服务器时间: " + LocalDateTime.now() + "  ： " + textWebSocketFrame.text()));
	}

	/**
	 * 客户端连接的时候触发
	 * @param ctx ChannelHandlerContext
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) {
		log.info("客户端进行链接");
	}

	/**
	 * 客户端断开连接的时候触发
	 * @param ctx ChannelHandlerContext
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) {
		log.info("客户端断开链接");
	}

	/**
	 * 异常处理
	 * @param ctx ChannelHandlerContext
	 * @param cause Throwable
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		log.error("netty服务链接异常：", cause);
		cause.printStackTrace();
		ctx.close();
	}

}
