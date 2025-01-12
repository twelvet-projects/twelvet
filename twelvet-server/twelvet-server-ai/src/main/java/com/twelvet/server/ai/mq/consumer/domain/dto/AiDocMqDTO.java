package com.twelvet.server.ai.mq.consumer.domain.dto;

import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.api.ai.domain.dto.AiDocDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * 处理添加RAG文档消息DTO
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
public class AiDocMqDTO extends AiDocDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 操作者
	 */
	@Schema(description = "更新者")
	@ExcelProperty("更新者")
	private String operatorBy;

	public String getOperatorBy() {
		return operatorBy;
	}

	public void setOperatorBy(String operatorBy) {
		this.operatorBy = operatorBy;
	}

	@Override
	public String toString() {
		return "AiDocMqDTO{" + "operatorBy='" + operatorBy + '\'' + '}';
	}

}
