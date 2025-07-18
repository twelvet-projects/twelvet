package com.twelvet.framework.datascope.service.impl;

import com.twelvet.api.system.feign.RemoteDeptService;
import com.twelvet.framework.datascope.service.MicroDataScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 分布式权限服务
 */
@Service
public class MicroDataScopeServiceImpl implements MicroDataScopeService {

	@Autowired
	private RemoteDeptService remoteDeptService;

}
