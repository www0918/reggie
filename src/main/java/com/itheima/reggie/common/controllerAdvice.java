package com.itheima.reggie.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/*
全局异常捕捉
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class controllerAdvice {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> Exception(SQLIntegrityConstraintViolationException exception){
        if (exception.getMessage().contains("Duplicate")){
            String[] s = exception.getMessage().split(" ");
            String msg=s[2]+"用户名已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }
    @ExceptionHandler(CustomException.class)
    public R<String> Exception(CustomException exception){

        return R.error(exception.getMessage());
    }
}
