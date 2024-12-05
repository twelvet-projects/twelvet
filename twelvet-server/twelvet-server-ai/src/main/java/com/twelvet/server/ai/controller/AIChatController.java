package com.twelvet.server.ai.controller;

import com.twelvet.api.ai.domain.dto.MessageDTO;
import com.twelvet.api.ai.domain.vo.MessageVO;
import com.twelvet.server.ai.service.AIChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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

	@Autowired
	private AIChatService aiChatService;

	@Operation(summary = "回答用户问题")
	@PreAuthorize("@role.hasPermi('ai:chat')")
	@PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<MessageVO> genAnswer(@RequestBody MessageDTO messageDTO) {
		return aiChatService.chatStream(messageDTO);
	}

	@Operation(summary = "格式化输出")
	@PostMapping("/format")
	public Flux<MessageVO> formatTest(@RequestBody MessageDTO messageDTO) {
		return aiChatService.formatTest(messageDTO);
	}

	@Operation(summary = "工具调用")
	@PostMapping("/fun")
	public Flux<MessageVO> funTest(@RequestBody MessageDTO messageDTO) {
		return aiChatService.funTest(messageDTO);
	}

}
