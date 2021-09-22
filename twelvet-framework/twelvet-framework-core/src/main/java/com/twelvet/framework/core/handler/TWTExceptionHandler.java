package com.twelvet.framework.core.handler;

import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.exception.TWTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;


/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 全局异常处理器
 */
@RestControllerAdvice
public class TWTExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(TWTExceptionHandler.class);

    /**
     * 基础异常
     *
     * @param e TWTException
     * @return AjaxResult
     */
    @ExceptionHandler(TWTException.class)
    public AjaxResult fastGoException(TWTException e) {
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 404异常
     *
     * @param e Exception
     * @return AjaxResult
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public AjaxResult handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(HttpStatus.NOT_FOUND.value(), "路径不存在，请检查路径是否正确");
    }

    /**
     * 处理业务校验过程中碰到的非法参数异常 该异常基本由{@link org.springframework.util.Assert}抛出
     *
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
     * validation Exception
     * 参数绑定异常
     *
     * @return AjaxResult
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public AjaxResult handleBodyValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        log.warn("参数绑定异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
        return AjaxResult.error(fieldErrors.get(0).getDefaultMessage());
    }

    /**
     * validation Exception (以form-data形式传参)
     * 参数绑定异常
     * @return AjaxResult
     */
    @ExceptionHandler({ BindException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AjaxResult bindExceptionHandler(BindException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        log.warn("参数绑定异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
        return AjaxResult.error(fieldErrors.get(0).getDefaultMessage());
    }

    /**
     * 无权限
     *
     * @param e AccessDeniedException
     * @return AjaxResult
     */
    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAuthorizationException(AccessDeniedException e) {
        log.error(e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN.value(), "没有权限，请联系管理员授权");
    }

    /**
     * @param e Exception
     * @return AjaxResult
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(e.getLocalizedMessage());
    }

}
