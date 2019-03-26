package com.hjhl.admin.handler;

import com.hjhl.admin.exception.GlobalException;
import com.hjhl.admin.util.ResultVOUtil;
import com.hjhl.admin.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.crazycake.shiro.exception.PrincipalInstanceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Set;

/**
 * 创建人: Hjx
 * Date: 2019/2/25
 * Description:
 */
@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(value = GlobalException.class)
    @ResponseBody
    public ResultVO GlobalExceptionHandle(GlobalException e){
        log.error("【公共自定异常】 code={}, msg={} ",
                e.getCode(), e.getMessage());
        return ResultVOUtil.Error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public ResultVO AuthorizationExceptionHandle(AuthorizationException e){
        log.error("【shiro权限不足】 msg={} ", e.getMessage());
        return ResultVOUtil.Error(HttpStatus.UNAUTHORIZED.value(), "权限不足以访问该链接");
    }

    @ExceptionHandler(value = PrincipalInstanceException.class)
    @ResponseBody
    public ResultVO PrincipalInstanceExceptionHandle(PrincipalInstanceException e){
        log.error("【shiro权限不足】 msg={} ", e.getMessage());
        return ResultVOUtil.Error(HttpStatus.UNAUTHORIZED.value(), "权限不足以访问该链接");
    }


    /**
     * 拦截捕捉自定义异常 ConstraintViolationException.class
     * 表单验证
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultVO ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        ConstraintViolation<?> cvl = iterator.next();
        String msg = cvl.getMessageTemplate();
        return ResultVOUtil.Error(HttpStatus.BAD_REQUEST.value(), msg);

    }
}
