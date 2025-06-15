package com.twelvet.server.ai.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.twelvet.api.ai.constant.ModelEnums;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.server.ai.model.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

import java.util.Comparator;
import java.util.Map;

/**
 * 模型工具
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-06-14
 */
public class ModelUtils {

	public final static Logger log = LoggerFactory.getLogger(ModelUtils.class);

	/**
	 * 已注册的模型供应商
	 */
	public final static Map<String, ModelService> MODEL_SERVICE_MAP = SpringUtil.getBeansOfType(ModelService.class);

	/**
	 * 获取对应模型供应商服务
	 * @return ModelService
	 */
	public static ModelService modelServiceProviderSelector(ModelEnums.ModelProviderEnums modelProviderEnums) {
		return MODEL_SERVICE_MAP.values()
			.stream()
			.filter(modelService -> modelService.support(modelProviderEnums))
			.max(Comparator.comparingInt(Ordered::getOrder))
			.orElseThrow(() -> new TWTException("ModelService error , not register"));
	}

}
