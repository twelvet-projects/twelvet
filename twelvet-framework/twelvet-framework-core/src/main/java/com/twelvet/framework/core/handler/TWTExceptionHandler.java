package com.twelvet.framework.core.handler;

import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.exception.NotFoundException;
import com.twelvet.framework.core.exception.TWTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 全局异常处理器
 */
@RestControllerAdvice
public class TWTExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(TWTExceptionHandler.class);

	/**
	 * 全局异常
	 * @param e Exception
	 * @return JsonResult
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public AjaxResult handleException(Exception e) {
		log.error(e.getMessage(), e);
		return AjaxResult.error(e.getLocalizedMessage());
	}

	/**
	 * 404
	 * @param e Exception
	 * @return JsonResult
	 */
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public AjaxResult handleNotFoundException(Exception e) {
		log.error(e.getMessage(), e);
		return AjaxResult.error(e.getLocalizedMessage());
	}

	/**
	 * 基础异常
	 * @param e TWTException
	 * @return JsonResult
	 */
	@ExceptionHandler(TWTException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public AjaxResult fastGoException(TWTException e) {
		return AjaxResult.error(e.getMessage());
	}

	/**
	 * 处理业务校验过程中碰到的非法参数异常 该异常基本由{@link org.springframework.util.Assert}抛出
	 * @param exception 参数校验异常
	 * @return API返回结果对象包装后的错误输出结果
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.OK)
	public AjaxResult handleIllegalArgumentException(IllegalArgumentException exception) {
		log.error("非法参数,ex = {}", exception.getMessage(), exception);
		return AjaxResult.error(exception.getMessage());
	}

	/**
	 * validation Exception 参数绑定异常
	 * @return JsonResult
	 */
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public AjaxResult handleBodyValidException(MethodArgumentNotValidException exception) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		log.warn("参数绑定异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
		return AjaxResult.error(fieldErrors.get(0).getDefaultMessage());
	}

	/**
	 * validation Exception (以form-data形式传参) 参数绑定异常
	 * @return JsonResult
	 */
	@ExceptionHandler({ BindException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public AjaxResult bindExceptionHandler(BindException exception) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		log.error("参数绑定异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
		return AjaxResult.error(fieldErrors.get(0).getDefaultMessage());
	}

	/**
	 * 请求方式不支持
	 * @param e HttpRequestMethodNotSupportedException
	 * @return JsonResult
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public JsonResult<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
		return JsonResult.error(e.getMessage());
	}

}
