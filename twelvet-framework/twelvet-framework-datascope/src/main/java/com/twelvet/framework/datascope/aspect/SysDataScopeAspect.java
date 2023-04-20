package com.twelvet.framework.datascope.aspect;

import com.twelvet.api.system.domain.SysRole;
import com.twelvet.framework.core.application.domain.BaseEntity;
import com.twelvet.framework.datascope.annotation.SysDataScope;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.framework.utils.TUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 系统数据过滤AOP
 */
@Aspect
@Component
public class SysDataScopeAspect {

	/**
	 * 全部数据权限
	 */
	public static final String DATA_SCOPE_ALL = "1";

	/**
	 * 自定数据权限
	 */
	public static final String DATA_SCOPE_CUSTOM = "2";

	/**
	 * 部门数据权限
	 */
	public static final String DATA_SCOPE_DEPT = "3";

	/**
	 * 部门及以下数据权限
	 */
	public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

	/**
	 * 仅本人数据权限
	 */
	public static final String DATA_SCOPE_SELF = "5";

	/**
	 * 数据权限过滤关键字
	 */
	public static final String DATA_SCOPE = "dataScope";

	/**
	 * 配置织入点
	 */
	@Pointcut("@annotation(com.twelvet.framework.datascope.annotation.SysDataScope)")
	public void dataScopePointCut() {
	}

	@Before("dataScopePointCut()")
	public void doBefore(JoinPoint point) {
		handleDataScope(point);
	}

	protected void handleDataScope(final JoinPoint joinPoint) {
		// 获得注解
		SysDataScope controllerDataScope = getAnnotationLog(joinPoint);
		if (controllerDataScope == null) {
			return;
		}
		// 获取当前的用户
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (StringUtils.isNotNull(loginUser)) {
			// 如果是超级管理员，则不过滤数据
			if (!SecurityUtils.isAdmin(loginUser.getUserId())) {
				dataScopeFilter(joinPoint, loginUser, controllerDataScope.deptAlias(), controllerDataScope.userAlias());
			}
		}
	}

	/**
	 * 数据范围过滤
	 * @param joinPoint 切点
	 * @param user 用户
	 * @param deptAlias 部门别名
	 * @param userAlias 用户别名
	 */
	public static void dataScopeFilter(JoinPoint joinPoint, LoginUser user, String deptAlias, String userAlias) {
		StringBuilder sqlString = new StringBuilder();

		for (SysRole role : user.getRoles()) {
			String dataScope = role.getDataScope();
			if (DATA_SCOPE_ALL.equals(dataScope)) {
				sqlString = new StringBuilder();
				break;
			}
			else if (DATA_SCOPE_CUSTOM.equals(dataScope)) {
				sqlString.append(StringUtils.format(
						" OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", deptAlias,
						role.getRoleId()));
			}
			else if (DATA_SCOPE_DEPT.equals(dataScope)) {
				sqlString.append(StringUtils.format(" OR {}.dept_id = {} ", deptAlias, user.getDeptId()));
			}
			else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
				sqlString.append(StringUtils.format(
						" OR {}.dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) )",
						deptAlias, user.getDeptId(), user.getDeptId()));
			}
			else if (DATA_SCOPE_SELF.equals(dataScope)) {
				if (StringUtils.isNotBlank(userAlias)) {
					sqlString.append(StringUtils.format(" OR {}.user_id = {} ", userAlias, user.getUserId()));
				}
				else {
					// 数据权限为仅本人且没有userAlias别名不查询任何数据
					sqlString.append(" OR 1=0 ");
				}
			}
		}

		if (StringUtils.isNotBlank(sqlString.toString())) {
			Object params = joinPoint.getArgs()[0];
			if (StringUtils.isNotNull(params) && params instanceof BaseEntity baseEntity) {
				baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
			}
		}
	}

	/**
	 * 是否存在注解，如果存在就获取
	 * @param joinPoint JoinPoint
	 * @return 返回注解信息
	 */
	private SysDataScope getAnnotationLog(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (TUtils.isNotEmpty(method)) {
			return method.getAnnotation(SysDataScope.class);
		}
		return null;
	}

}
