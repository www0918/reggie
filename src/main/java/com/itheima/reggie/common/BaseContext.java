package com.itheima.reggie.common;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 编写BaseContext工具类，基于ThreadLocal封装的工具类
 */

public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public  static Long getCurrentId(){
        return threadLocal.get();
    }
}
