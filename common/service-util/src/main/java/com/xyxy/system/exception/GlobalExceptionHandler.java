package com.xyxy.system.exception;

import com.xyxy.common.result.Result;
import com.xyxy.common.result.ResultCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {


    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return  Result.fail().message("执行了全局异常处理");
    }


    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return  Result.fail().message("执行了特定异常处理");
    }

    //特定异常
    @ExceptionHandler(XyxyException.class)
    @ResponseBody
    public Result error(XyxyException e){
        e.printStackTrace();
        return  Result.fail().code(e.getCode()).message(e.getMsg());
    }

    /**
     * spring security异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) throws AccessDeniedException {
        return Result.fail().code(ResultCodeEnum.PERMISSION.getCode()).message("没有当前操作权限");
    }
}
