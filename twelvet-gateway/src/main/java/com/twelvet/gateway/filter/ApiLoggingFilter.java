package com.twelvet.gateway.filter;

import com.twelvet.framework.utils.DateUtils;
import com.twelvet.framework.utils.http.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 全局日志拦截器，对请求的API调用过滤，记录接口的请求时间，方便日志审计、告警、分析等运维操作 2. 后期可以扩展对接其他日志系统
 */
@Component
public class ApiLoggingFilter implements GlobalFilter, Ordered {

	private static final Logger log = LoggerFactory.getLogger(ApiLoggingFilter.class);

	private static final String START_TIME = "startTime";

	/**
	 * nginx需要配置
	 */
	private static final String X_REAL_IP = "X-Real-IP";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		if (log.isDebugEnabled()) {
			String info = String.format("Method:{%s} Host:{%s} Path:{%s} Query:{%s}", request.getMethod().name(),
					request.getURI().getHost(), request.getURI().getPath(), request.getQueryParams());
			log.debug(info);
		}
		exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			Long startTime = exchange.getAttribute(START_TIME);
			if (startTime != null) {
				Long executeTime = (System.currentTimeMillis() - startTime);
				List<String> ips = request.getHeaders().get(X_REAL_IP);
				String ip = ips != null ? ips.get(0) : null;
				String api = request.getURI().getRawPath();

				int statusCode = 500;
				if (exchange.getResponse().getStatusCode() != null) {
					statusCode = exchange.getResponse().getStatusCode().value();
				}
				// 当前仅记录日志，后续可以添加日志队列，来过滤请求慢的接口
				if (log.isDebugEnabled()) {
					log.debug(
							"\n===================Request================>\n来自IP地址：{}\n时间：{}\n地址：{}\n参数：{}\n方式：{}"
									+ "\n<===================Response================\n状态：{}\n时长：{}毫秒"
									+ "\n============================================",
							ip, DateUtils.getTime(), api, request.getQueryParams(), request.getMethod().name(),
							statusCode, executeTime

					);
				}
			}
		}));
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

}
