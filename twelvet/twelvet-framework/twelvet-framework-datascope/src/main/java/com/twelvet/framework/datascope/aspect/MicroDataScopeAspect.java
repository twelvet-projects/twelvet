package com.twelvet.framework.datascope.aspect;

import cn.hutool.core.collection.CollectionUtil;
import com.twelvet.api.system.domain.SysRole;
import com.twelvet.framework.core.application.domain.BaseEntity;
import com.twelvet.framework.datascope.annotation.MicroDataScope;
import com.twelvet.framework.datascope.constant.DataScopeConstants;
import com.twelvet.framework.datascope.domain.vo.DataScopeVO;
import com.twelvet.framework.datascope.service.MicroDataScopeService;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.StrUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 分布式数据权限
 */
@Aspect
@Component
public class MicroDataScopeAspect {

	@Autowired
	private MicroDataScopeService microDataScopeService;

	/**
	 * 配置织入点
	 */
	@Pointcut("@annotation(com.twelvet.framework.datascope.annotation.MicroDataScope)")
	public void dataScopePointCut() {
	}

	@Before("dataScopePointCut()")
	public void doBefore(JoinPoint point) {
		handleDataScope(point);
	}

	protected void handleDataScope(final JoinPoint joinPoint) {
		// 获得注解
		MicroDataScope controllerDataScope = getAnnotationLog(joinPoint);
		if (controllerDataScope == null) {
			return;
		}
		// 获取当前的用户
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (StrUtils.isNotNull(loginUser)) {
			// 如果是超级管理员，则不过滤数据
			if (!SecurityUtils.isAdmin(loginUser.getUserId())) {
				dataScopeFilter(joinPoint, loginUser, controllerDataScope.deptIdField(),
						controllerDataScope.userIdField());
			}
		}
	}

	/**
	 * 数据范围过滤
	 * @param joinPoint 切点
	 * @param user 用户
	 * @param deptIdField 部门别名
	 * @param userIdField 用户别名
	 */
	public static void dataScopeFilter(JoinPoint joinPoint, LoginUser user, String deptIdField, String userIdField) {
		StringBuilder sqlString = new StringBuilder();
		DataScopeVO dataScopeVO = new DataScopeVO();

		// 包含所有角色ID
		Set<Long> deptIdSet = dataScopeVO.getDeptIdSet();

		// 是否已经使用了deptId
		boolean deptFlag = Boolean.TRUE;
		// 是否已经使用了userId
		boolean userFlag = Boolean.TRUE;

		// 默认没有权限查不出权限
		String deptIdStr = "0";
		if (CollectionUtil.isNotEmpty(deptIdSet)) {
			deptIdStr = deptIdSet.stream().map(Object::toString).collect(Collectors.joining(","));
		}

		for (SysRole role : user.getRoles()) {
			String dataScope = role.getDataScope();
			if (DataScopeConstants.DATA_SCOPE_ALL.equals(dataScope)) { // 只要有一个可看所有数据跳出
				sqlString = new StringBuilder();
				break;
			}
			else if (deptFlag && (DataScopeConstants.DATA_SCOPE_CUSTOM.equals(dataScope)
					|| DataScopeConstants.DATA_SCOPE_DEPT.equals(dataScope)
					|| DataScopeConstants.DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope))) { // 只使用一次dept
				deptFlag = Boolean.FALSE;
				sqlString.append(StrUtils.format(" OR {} IN ( {} ) ", deptIdField, deptIdStr));
			}
			else if (userFlag && DataScopeConstants.DATA_SCOPE_SELF.equals(dataScope)) { // 只使用一次user
				userFlag = Boolean.FALSE;
				sqlString.append(StrUtils.format(" OR {} = {} ", userIdField, user.getUserId()));
			}
		}

		if (StringUtils.isNotBlank(sqlString.toString())) {
			Object params = joinPoint.getArgs()[0];
			if (StrUtils.isNotNull(params) && params instanceof BaseEntity baseEntity) {
				baseEntity.getParams().put(DataScopeConstants.DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
			}
		}
	}

	/**
	 * 是否存在注解，如果存在就获取
	 * @param joinPoint JoinPoint
	 * @return 返回注解信息
	 */
	private MicroDataScope getAnnotationLog(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (Objects.nonNull(method)) {
			return method.getAnnotation(MicroDataScope.class);
		}
		return null;
	}

}
