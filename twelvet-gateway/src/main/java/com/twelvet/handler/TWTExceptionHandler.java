package com.twelvet.handler;

import com.alibaba.fastjson.JSON;
import com.twelvet.domain.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 网关异常处理器，只作用在webflux 环境下 , 优先级低于 {@link ResponseStatusExceptionHandler} 执行
 */
@Order(-1)
@Configuration
public class TWTExceptionHandler implements ErrorWebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(TWTExceptionHandler.class);

    /**
     * 重写处理器
     *
     * @param serverWebExchange serverWebExchange
     * @param throwable         throwable
     * @return Mono
     */
    @Override
    @NonNull
    public Mono<Void> handle(ServerWebExchange serverWebExchange, @NonNull Throwable throwable) {
        ServerHttpResponse response = serverWebExchange.getResponse();

        if (serverWebExchange.getResponse().isCommitted()) {
            return Mono.error(throwable);
        }

        // 按照异常类型进行处理
        int code;
        String msg;
        if (throwable instanceof NotFoundException) {
            code = HttpStatus.SERVICE_UNAVAILABLE.value();
            // HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase()
            msg = "服务未开启";
        } else if (throwable instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) throwable;
            code = responseStatusException.getStatus().value();
            msg = responseStatusException.getStatus().getReasonPhrase();
        } else {
            code = HttpStatus.INTERNAL_SERVER_ERROR.value();
            msg = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        }

        log.error("[网关异常处理]请求路径:{},异常信息:{}", serverWebExchange.getRequest().getPath(), throwable.getMessage());

        // 设置响应头
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        // 设置响应的状态
        response.setStatusCode(HttpStatus.OK);

        // 返回信息
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JSON.toJSONBytes(R.fail(code, msg)));
        }));

    }

}
