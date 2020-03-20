package com.tianhy.mybatis.version2.plugin;

import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * {@link}
 *
 * @Desc: 包装类，对被代理对象进行包装
 * @Author: thy
 * @CreateTime: 2019/5/8
 **/
@Data
public class Invocation {

    private Object target;
    private Method method;
    private Object[] args;

    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object proceed() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(target, args);
    }

}
