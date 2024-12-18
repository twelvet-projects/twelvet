package com.twelvet.server.ai.service;

import com.twelvet.api.ai.domain.dto.MessageDTO;
import com.twelvet.api.ai.domain.vo.MessageVO;
import com.twelvet.server.ai.fun.vo.ActorsFilms;
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
	 * tts文字转语音
	 * @param messageDTO MessageDTO
	 * @return 流式数据返回
	 */
	Flux<MessageVO> tts(MessageDTO messageDTO);

	/**
	 * stt语音转文字
	 * @param messageDTO MessageDTO
	 * @return 流式数据返回
	 */
	Flux<MessageVO> stt(MessageDTO messageDTO);

}
