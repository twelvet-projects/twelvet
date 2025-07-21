package com.twelvet.framework.datascope.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.twelvet.api.system.domain.SysDept;
import com.twelvet.api.system.domain.SysRole;
import com.twelvet.api.system.feign.RemoteDeptService;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.datascope.constant.DataScopeConstants;
import com.twelvet.framework.datascope.service.MicroDataScopeService;
import com.twelvet.framework.redis.service.RedisUtils;
import com.twelvet.framework.redis.service.constants.CacheConstants;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.StrUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Objects;
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

	/**
	 * 获取权限
	 * @return Set<Long>
	 */
	@Override
	public Set<Long> getPermission() {
		// 获取当前的用户
		LoginUser loginUser = SecurityUtils.getLoginUser();
		Set<Long> permissionSet = new HashSet<>();

		String cacheKey = String.format(CacheConstants.DATA_SCOPE_CACHE, loginUser.getUserId());

		Set<Long> permissionSetCache = RedisUtils.getCacheObject(cacheKey);
		if (Objects.nonNull(permissionSetCache)) { // 空数组也允许获取，取缓存权限
			return permissionSetCache;
		}

		for (SysRole role : loginUser.getRoles()) {
			String dataScope = role.getDataScope();

			if (DataScopeConstants.DATA_SCOPE_ALL.equals(dataScope)) { // 只要有一个可看所有数据跳出
				permissionSet = new HashSet<>();
				break;
			}
			else if ((DataScopeConstants.DATA_SCOPE_CUSTOM.equals(dataScope)
					|| DataScopeConstants.DATA_SCOPE_DEPT.equals(dataScope)
					|| DataScopeConstants.DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope))) { // 只使用一次dept
				R<Set<Long>> remote = remoteDeptService.selectDeptIdListByUser();
				permissionSet = remote.getData();
			}
			// 无需要获取自身数据权限
		}

		// 写入缓存
		RedisUtils.setCacheObject(cacheKey, permissionSet);

		return permissionSet;
	}

}
