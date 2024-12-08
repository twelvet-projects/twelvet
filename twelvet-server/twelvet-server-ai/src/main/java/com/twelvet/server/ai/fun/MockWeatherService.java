package com.twelvet.server.ai.fun;

import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.twelvet.server.ai.fun.vo.ResponseVO;
import com.twelvet.server.ai.fun.vo.Request;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

/**
 * Title Mock weather service.<br>
 * Description Mock weather service.<br>
 *
 * @author yuanci.ytb
 * @since 2024/8/16 11:29
 */
@Description("根据城市查询指定时间的天气")
@Service
public class MockWeatherService implements Function<Request, ResponseVO> {

	@Override
	public ResponseVO apply(Request request) {
		if (request.city().contains("杭州")) {
			return new ResponseVO(String.format("%s%s晴转多云, 气温32摄氏度。", request.date(), request.city()));
		}
		else if (request.city().contains("上海")) {
			return new ResponseVO(String.format("%s%s多云转阴, 气温31摄氏度。", request.date(), request.city()));
		}
		else {
			return new ResponseVO(String.format("暂时无法查询%s的天气状况。", request.city()));
		}
	}

}