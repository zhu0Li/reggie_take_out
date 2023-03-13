package com.reggie.common;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登灵用户id
 * 作用于某一线程之内，具有线程隔离功能
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    //设置值
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    //获取值
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
