package com.twelvet.server.ai.service;

import com.twelvet.server.ai.domain.dto.MessageDTO;
import com.twelvet.server.ai.domain.vo.MessageVO;
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

}
