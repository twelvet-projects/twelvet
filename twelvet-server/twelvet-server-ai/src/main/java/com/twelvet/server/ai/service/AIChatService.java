package com.twelvet.server.ai.service;

import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisOutput;
import com.twelvet.api.ai.domain.AiChatHistory;
import com.twelvet.api.ai.domain.dto.MessageDTO;
import com.twelvet.api.ai.domain.dto.SttDTO;
import com.twelvet.api.ai.domain.dto.TtsDTO;
import com.twelvet.api.ai.domain.vo.AiChatHistoryPageVO;
import com.twelvet.api.ai.domain.vo.MessageVO;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * AI助手服务
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-10-26
 */
public interface AIChatService {

	/**
	 * 发起聊天
	 * @param messageDTO MessageDTO
	 * @return 流式数据返回
	 */
	Flux<MessageVO> chatStream(MessageDTO messageDTO);

	/**
	 * 根据知识库ID获取聊天记录分页
	 * @param aiChatHistory AiChatHistory
	 * @return AiChatHistoryPageVO
	 */
	List<AiChatHistoryPageVO> chatHistoryPage(AiChatHistory aiChatHistory);

	/**
	 * 多模态回答用户问题
	 * @param messageDTO MessageDTO
	 * @return 流式数据返回
	 */
	Flux<MessageVO> multiChatStream(MessageDTO messageDTO);

	/**
	 * OCR格式化识别
	 * @param messageDTO MessageDTO
	 * @return 流式数据返回
	 */
	Flux<MessageVO> ocrChatStream(MessageDTO messageDTO);

	/**
	 * 文生图
	 * @param messageDTO MessageDTO
	 * @return 流式数据返回
	 */
	Flux<MessageVO> ttiChatStream(MessageDTO messageDTO);

	/**
	 * 文生视频
	 * @param messageDTO MessageDTO
	 * @return 流式数据返回
	 */
	Flux<MessageVO> itvChatStream(MessageDTO messageDTO);

	/**
	 * tts文字转语音
	 * @param ttsDTO MessageDTO
	 * @return 流式数据返回
	 */
	SpeechSynthesisOutput tts(TtsDTO ttsDTO);

	/**
	 * stt语音转文字
	 * @param sttDTO MessageDTO
	 * @return 流式数据返回
	 */
	String stt(SttDTO sttDTO);

}
