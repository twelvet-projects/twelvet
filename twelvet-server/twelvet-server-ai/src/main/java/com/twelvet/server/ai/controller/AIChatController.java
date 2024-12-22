package com.twelvet.server.ai.controller;

import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisOutput;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisResult;
import com.twelvet.api.ai.domain.dto.MessageDTO;
import com.twelvet.api.ai.domain.dto.SttDTO;
import com.twelvet.api.ai.domain.dto.TtsDTO;
import com.twelvet.api.ai.domain.vo.MessageVO;
import com.twelvet.framework.core.domain.R;
import com.twelvet.server.ai.service.AIChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
	public Flux<MessageVO> chatStream(@Validated @RequestBody MessageDTO messageDTO) {
		return aiChatService.chatStream(messageDTO);
	}

	/**
	 * 多模态回答用户问题
	 * @param messageDTO MessageDTO
	 * @return 流式输出回复
	 */
	@Operation(summary = "多模态回答用户问题")
	@PreAuthorize("@role.hasPermi('ai:chat')")
	@PostMapping(value = "/multi", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<MessageVO> multiChatStream(@Validated @RequestBody MessageDTO messageDTO) {
		return aiChatService.multiChatStream(messageDTO);
	}

	/**
	 * tts文字转语音
	 * @param ttsDTO MessageDTO
	 * @return 流式输出回复
	 */
	@Operation(summary = "tts文字转语音")
	@PreAuthorize("@role.hasPermi('ai:chat')")
	@PostMapping("/tts")
	public R<SpeechSynthesisOutput> tts(@Validated @RequestBody TtsDTO ttsDTO) {
		return R.ok(aiChatService.tts(ttsDTO));
	}

	/**
	 * stt语音转文字
	 * @param sttDTO MessageDTO
	 * @return 流式输出回复
	 */
	@Operation(summary = "stt语音转文字")
	@PreAuthorize("@role.hasPermi('ai:chat')")
	@PostMapping("/stt")
	public R<String> stt(@Validated @ModelAttribute SttDTO sttDTO) {
		return R.ok(aiChatService.stt(sttDTO));
	}

}
