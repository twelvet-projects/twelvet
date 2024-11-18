package com.twelvet.server.ai.service.impl;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiMessageChatMemory implements ChatMemory {

	/**
	 * 不实现新增
	 * @param conversationId
	 * @param messages
	 */
	@Override
	public void add(String conversationId, List<Message> messages) {

	}

	/**
	 * 获取历史内容的地方
	 * @param conversationId
	 * @param lastN
	 * @return
	 */
	@Override
	public List<Message> get(String conversationId, int lastN) {
		return List.of();
	}

	/**
	 * 清楚会话内容
	 * @param conversationId
	 */
	@Override
	public void clear(String conversationId) {

	}

}
