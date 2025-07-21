package com.twelvet.api.system.feign.factory;

import com.twelvet.api.system.domain.SysDept;
import com.twelvet.api.system.feign.RemoteDeptService;
import com.twelvet.framework.core.domain.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 部门服务降级处理
 */
@Component
public class RemoteDeptFallbackFactory implements FallbackFactory<RemoteDeptService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteDeptFallbackFactory.class);

	@Override
	public RemoteDeptService create(Throwable throwable) {
		log.error("部门服务调用失败:{}", throwable.getMessage());
		return new RemoteDeptService() {
			@Override
			public R<Set<Long>> selectDeptIdListByUser() {
				return R.fail();
			}
		};
	}

}
