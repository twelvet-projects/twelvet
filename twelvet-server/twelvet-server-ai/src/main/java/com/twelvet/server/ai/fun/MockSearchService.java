package com.twelvet.server.ai.fun;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.twelvet.server.ai.fun.vo.ResponseVO;
import org.springframework.ai.tool.annotation.Tool;

/**
 * 模拟接入联网工具
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-07-06
 */
public class MockSearchService {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonClassDescription("根据日期和城市查询天气")
	public record Request(
			@JsonProperty(required = true, value = "query") @JsonPropertyDescription("需要查询的内容") String query) {
	}

	@Tool(description = "使用 Search API 进行搜索互联网网页，输入应为搜索查询字符串，输出将返回搜索结果的详细信息，包括网页标题、网页URL、网页摘要、网站名称、网站Icon、网页发布时间等。")
	public ResponseVO apply(MockSearchService.Request request) {
		return new ResponseVO("mock搜索结果，你可以自由回答");
	}

}
