package com.twelvet.server.ai.controller;

import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisOutput;
import com.twelvet.api.ai.domain.AiChatHistory;
import com.twelvet.api.ai.domain.AiModel;
import com.twelvet.api.ai.domain.dto.AiDocDTO;
import com.twelvet.api.ai.domain.dto.MessageDTO;
import com.twelvet.api.ai.domain.dto.SttDTO;
import com.twelvet.api.ai.domain.dto.TtsDTO;
import com.twelvet.api.ai.domain.vo.AiChatHistoryPageVO;
import com.twelvet.api.ai.domain.vo.AiModelVO;
import com.twelvet.api.ai.domain.vo.MessageVO;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.server.ai.service.AIChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

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

	private final AIChatService aiChatService;

	public AIChatController(AIChatService aiChatService) {
		this.aiChatService = aiChatService;
	}

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
	 * 根据知识库ID获取聊天记录分页
	 */
	@Operation(summary = "查询AI大模型分页")
	@PreAuthorize("@role.hasPermi('ai:chat:history')")
	@GetMapping("/history/page")
	public JsonResult<TableDataInfo<AiChatHistoryPageVO>> chatHistoryPage(AiChatHistory aiChatHistory) {
		PageUtils.startPage();
		List<AiChatHistoryPageVO> list = aiChatService.chatHistoryPage(aiChatHistory);
		return JsonResult.success(PageUtils.getDataTable(list));
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
	 * OCR格式化识别
	 * @param messageDTO MessageDTO
	 * @return 流式输出回复
	 */
	@Operation(summary = "OCR格式化识别")
	@PreAuthorize("@role.hasPermi('ai:chat')")
	@PostMapping(value = "/ocr", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<MessageVO> ocrChatStream(@Validated @RequestBody MessageDTO messageDTO) {
		return aiChatService.ocrChatStream(messageDTO);
	}

	/**
	 * 文生图
	 * @param messageDTO MessageDTO
	 * @return 流式输出回复
	 */
	@Operation(summary = "文生图")
	@PreAuthorize("@role.hasPermi('ai:chat')")
	@PostMapping(value = "/tti", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<MessageVO> ttiChatStream(@Validated @RequestBody MessageDTO messageDTO) {
		return aiChatService.ttiChatStream(messageDTO);
	}

	/**
	 * 图生视频
	 * @param messageDTO MessageDTO
	 * @return 流式输出回复
	 */
	@Operation(summary = "图生视频")
	@PreAuthorize("@role.hasPermi('ai:chat')")
	@PostMapping(value = "/itv", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<MessageVO> itvChatStream(@Validated @RequestBody MessageDTO messageDTO) {
		return aiChatService.itvChatStream(messageDTO);
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
