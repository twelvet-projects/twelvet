package com.twelvet.server.ai.fun;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.twelvet.server.ai.fun.vo.ResponseVO;
import org.springframework.ai.tool.annotation.Tool;

/**
 * Title Mock order service.<br>
 * Description Mock order service.<br>
 *
 * @author yuanci.ytb
 * @since 2024/8/16 11:29
 */
public class MockOrderService {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public record Request(
			@JsonProperty(required = true,
					value = "orderId") @JsonPropertyDescription("订单编号, 比如1001***") String orderId,
			@JsonProperty(required = true,
					value = "userId") @JsonPropertyDescription("用户编号, 比如2001***") String userId) {
	}

	@Tool(description = "根据用户ID以及商品ID获取购买的商品")
	public ResponseVO getOrder(Request request) {
		String productName = "尤尼克斯羽毛球拍";
		return new ResponseVO(String.format("%s的订单编号为%s, 购买的商品为: %s", request.userId, request.orderId, productName));
	}

}