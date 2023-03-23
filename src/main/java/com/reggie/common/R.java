package com.reggie.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 此类是一个通用返回结果类，
 * 服务端响应的所有结果最终都会包装成此种类型返回给前端页面
 * @param <T>
 */
@Data
public class R<T> implements Serializable {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据，表示返回值是一个泛型，传递啥，就返回啥类型的数据

    private Map map = new HashMap(); //动态数据

    public static <T> R<T> success(T object) { //需要传进来数据体，以方便储存等操作
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) { //需要传进来错误信息msg
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
