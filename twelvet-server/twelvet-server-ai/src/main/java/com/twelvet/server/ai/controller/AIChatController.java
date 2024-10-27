package com.twelvet.server.ai.controller;

import com.twelvet.framework.security.annotation.AuthIgnore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * AI助手Controller
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-10-26
 */
@Tag(description = "AIChatController", name = "AI助手")
@RestController
@RequestMapping("/chat")
public class AIChatController {


    /**
     * 查询国际化分页
     */
    @AuthIgnore(value = false)
    @Operation(summary = "查询国际化分页")
    @GetMapping()
    public SseEmitter pageQuery() {
        SseEmitter emitter = new SseEmitter();

        // 创建一个线程池来发送事件
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    // 每隔1秒发送一个事件
                    emitter.send("Event " + i);
                    TimeUnit.SECONDS.sleep(1);
                }
                emitter.complete(); // 完成事件
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e); // 出现错误时完成
            }
        });

        return emitter;
    }

}
