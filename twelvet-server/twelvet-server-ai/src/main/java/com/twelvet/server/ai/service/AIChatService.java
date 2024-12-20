package com.twelvet.server.ai.service;

import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisResponse;
import com.twelvet.api.ai.domain.dto.MessageDTO;
import com.twelvet.api.ai.domain.vo.MessageVO;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import reactor.core.publisher.Flux;

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
	 * tts文字转语音
	 * @param messageDTO MessageDTO
	 * @return 流式数据返回
	 */
	SpeechSynthesisResponse tts(MessageDTO messageDTO);

	/**
	 * stt语音转文字
	 * @param messageDTO MessageDTO
	 * @return 流式数据返回
	 */
	AudioTranscriptionResponse stt(MessageDTO messageDTO);

}
