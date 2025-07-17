package com.twelvet.framework.datascope.aspect;

import com.twelvet.api.system.domain.SysRole;
import com.twelvet.framework.core.application.domain.BaseEntity;
import com.twelvet.framework.datascope.annotation.MicroDataScope;
import com.twelvet.framework.datascope.constant.DataScopeConstants;
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
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 分布式数据权限
 */
@Aspect
@Component
public class MicroDataScopeAspect {

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
			if (DataScopeConstants.DATA_SCOPE_ALL.equals(dataScope)) {
				sqlString = new StringBuilder();
				break;
			}
			else if (DataScopeConstants.DATA_SCOPE_CUSTOM.equals(dataScope)) {
				sqlString.append(
						StrUtils.format(" OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ",
								deptAlias, role.getRoleId()));
			}
			else if (DataScopeConstants.DATA_SCOPE_DEPT.equals(dataScope)) {
				sqlString.append(StrUtils.format(" OR {}.dept_id = {} ", deptAlias, user.getDeptId()));
			}
			else if (DataScopeConstants.DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
				sqlString.append(StrUtils.format(
						" OR {}.dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) )",
						deptAlias, user.getDeptId(), user.getDeptId()));
			}
			else if (DataScopeConstants.DATA_SCOPE_SELF.equals(dataScope)) {
				if (StringUtils.isNotBlank(userAlias)) {
					sqlString.append(StrUtils.format(" OR {}.user_id = {} ", userAlias, user.getUserId()));
				}
				else {
					// 数据权限为仅本人且没有userAlias别名不查询任何数据
					sqlString.append(" OR 1=0 ");
				}
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
