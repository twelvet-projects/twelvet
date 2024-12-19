package com.twelvet.server.ai.controller;

import com.twelvet.api.ai.domain.dto.MessageDTO;
import com.twelvet.api.ai.domain.vo.MessageVO;
import com.twelvet.server.ai.service.AIChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

	/**
	 * 回答用户问题
	 * @param messageDTO MessageDTO
	 * @return 流式输出回复
	 */
	@Operation(summary = "回答用户问题")
	@PreAuthorize("@role.hasPermi('ai:chat')")
	@PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<MessageVO> chatStream(@RequestBody MessageDTO messageDTO) {
		return aiChatService.chatStream(messageDTO);
	}

	/**
	 * tts文字转语音
	 * @param messageDTO MessageDTO
	 * @return 流式输出回复
	 */
	@Operation(summary = "回答用户问题")
	@PreAuthorize("@role.hasPermi('ai:chat')")
	@PostMapping("/tts")
	public Flux<MessageVO> tts(@RequestBody MessageDTO messageDTO) {
		return aiChatService.tts(messageDTO);
	}

	/**
	 * stt语音转文字
	 * @param messageDTO MessageDTO
	 * @return 流式输出回复
	 */
	@Operation(summary = "回答用户问题")
	@PreAuthorize("@role.hasPermi('ai:chat')")
	@PostMapping("/stt")
	public Flux<MessageVO> stt(@RequestBody MessageDTO messageDTO) {
		return aiChatService.stt(messageDTO);
	}

}
