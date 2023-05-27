package com.twelvet.framework.log.aspect;

import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessStatus;
import com.twelvet.framework.log.event.SysOperationLogEvent;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.framework.utils.SpringContextHolder;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.framework.utils.TUtils;
import com.twelvet.framework.utils.http.IpUtils;
import com.twelvet.framework.utils.http.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 操作日志记录处理
 */
@Aspect
public class SysLogAspect {

	private static final Logger log = LoggerFactory.getLogger(SysLogAspect.class);

	/**
	 * 配置切入点
	 */
	@Pointcut("@annotation(com.twelvet.framework.log.annotation.Log)")
	public void logPointCut() {
	}

	/**
	 * 处理完请求后执行
	 * @param joinPoint 切点
	 */
	@AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
	public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
		handleLog(joinPoint, null, jsonResult);
	}

	/**
	 * 拦截异常操作
	 * @param joinPoint 切点
	 * @param e 异常
	 */
	@AfterThrowing(value = "logPointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
		handleLog(joinPoint, e, null);
	}

	protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
		try {
			// 获得注解
			Log controllerLog = getAnnotationLog(joinPoint);
			if (controllerLog == null) {
				return;
			}

			// 获取当前的用户
			LoginUser loginUser = SecurityUtils.getLoginUser();

			// *========数据库日志=========*//
			SysOperationLog operationLog = new SysOperationLog();
			operationLog.setStatus(BusinessStatus.SUCCESS.value());
			// 请求的地址
			String ip = IpUtils.getIpAddr(ServletUtils.getRequest().get());
			operationLog.setOperIp(ip);

			// 返回参数
			operationLog.setJsonResult(JacksonUtils.toJson(jsonResult));

			operationLog.setOperUrl(ServletUtils.getRequest().get().getRequestURI());
			if (loginUser != null) {
				operationLog.setOperName(loginUser.getUsername());
				operationLog.setDeptId(loginUser.getDeptId());
			}

			if (e != null) {
				operationLog.setStatus(BusinessStatus.FAIL.value());
				operationLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
			}
			// 设置方法名称
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			operationLog.setMethod(className + "." + methodName + "()");
			// 设置请求方式
			operationLog.setRequestMethod(ServletUtils.getRequest().get().getMethod());
			// 处理设置注解上的参数
			getControllerMethodDescription(joinPoint, controllerLog, operationLog);
			// 发送Spring事件保存数据库
			SpringContextHolder.publishEvent(new SysOperationLogEvent(operationLog));
		}
		catch (Exception exp) {
			// 记录本地异常日志
			log.error("==前置通知异常==");
			log.error("异常信息:{}", exp.getMessage());
			exp.printStackTrace();
		}
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * @param log 日志
	 * @param operationLog 操作日志
	 */
	public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperationLog operationLog) {
		// 设置action动作
		operationLog.setBusinessType(log.businessType().ordinal());
		// 设置标题
		operationLog.setService(log.service());
		// 设置操作人类别
		operationLog.setOperatorType(log.operatorType().ordinal());
		// 是否需要保存request，参数和值
		if (log.isSaveRequestData()) {
			// 获取参数的信息，传入到数据库中。
			setRequestValue(joinPoint, operationLog);
		}
	}

	/**
	 * 获取请求的参数，放到log中
	 * @param operationLog 操作日志
	 */
	private void setRequestValue(JoinPoint joinPoint, SysOperationLog operationLog) {
		String requestMethod = operationLog.getRequestMethod();
		if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
			String params;
			String contentType = ServletUtils.getRequest().get().getContentType();
			if (!TUtils.isEmpty(contentType) && contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
				params = "FILE";
			}
			else {
				try {
					params = argsArrayToString(joinPoint.getArgs());
				}
				catch (IOException e) {
					throw new TWTException("参数拼装失败");
				}
			}

			operationLog.setOperParam(StringUtils.substring(params, 0, 2000));
		}
		else {
			Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest()
				.get()
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			operationLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 1000));
		}
	}

	/**
	 * 是否存在注解，如果存在就获取
	 * @param joinPoint JoinPoint
	 * @return Log
	 */
	private Log getAnnotationLog(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(Log.class);
		}
		return null;
	}

	/**
	 * 参数拼装
	 */
	private String argsArrayToString(Object[] paramsArray) throws IOException {
		StringBuilder params = new StringBuilder();
		if (paramsArray != null && paramsArray.length > 0) {
			for (Object o : paramsArray) {
				if (!isFilterObject(o)) {
					params.append(JacksonUtils.toJson(o)).append(" ");
				}
			}
		}
		return params.toString().trim();
	}

	/**
	 * 判断是否需要过滤的对象。
	 * @param o 对象信息。
	 * @return 如果是需要过滤的对象，则返回true；否则返回false。
	 */
	public boolean isFilterObject(final Object o) {
		return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
	}

}
