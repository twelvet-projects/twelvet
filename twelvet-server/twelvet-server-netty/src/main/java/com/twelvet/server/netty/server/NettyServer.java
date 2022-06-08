package com.twelvet.server.netty.server;

import com.twelvet.framework.utils.$;
import com.twelvet.server.netty.properties.NettyProperties;
import com.twelvet.server.netty.server.handler.WebSocketServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 启动Netty
 */
@Service
public class NettyServer
		implements ApplicationRunner, ApplicationListener<ContextClosedEvent>, ApplicationContextAware {

	private final static Logger log = LoggerFactory.getLogger(NettyServer.class);

	@Autowired
	private NettyProperties nettyProperties;

	/**
	 * Netty跟随springboot启动
	 * @param args ApplicationArguments
	 */
	@Override
	public void run(ApplicationArguments args) {

		$.threadPoolExecutor.execute(() -> {
			log.info("正在启动websocket服务器");

			// 主线程池（接受客户端请求链接）
			EventLoopGroup bossGroup = new NioEventLoopGroup();
			// 从线程池：主要处理主线程给予的任务
			EventLoopGroup workerGroup = new NioEventLoopGroup();
			try {
				// 创建服务启动类
				ServerBootstrap serverBootstrap = new ServerBootstrap();
				// 设置主从线程组
				serverBootstrap.group(bossGroup, workerGroup);

				serverBootstrap
						// 使用NIO通道作为实现
						.channel(NioServerSocketChannel.class)
						// 设置保持活动链接状态，心跳包
						.childOption(ChannelOption.SO_KEEPALIVE, true)
						// 设置处理器
						.childHandler(new WebSocketServerInitializer());

				int port = nettyProperties.getPort();

				Channel channel = serverBootstrap.bind(port).sync().channel();

				log.info("Websocket 成功启动，服务端口：{}", port);

				// 对关闭通道进行监听
				channel.closeFuture().sync();

			}
			catch (InterruptedException e) {
				log.info("Netty发生不可逆错误：", e);
			}
			finally {
				// 关闭线程池
				bossGroup.shutdownGracefully();
				workerGroup.shutdownGracefully();
			}
		});

	}

	/**
	 * 启动时触发
	 * @param applicationContext ApplicationContext
	 * @throws BeansException BeansException
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		log.info("websocket 服务启动");
	}

	/**
	 * 关闭时触发
	 * @param contextClosedEvent ContextClosedEvent
	 */
	@Override
	public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
		log.info("Netty 安全关闭");
	}

}
