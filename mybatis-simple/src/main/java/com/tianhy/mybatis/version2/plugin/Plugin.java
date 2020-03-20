package com.tianhy.mybatis.version2.plugin;

import com.tianhy.mybatis.version2.annotation.Intercepts;

import java.lang.reflect.*;

/**
 * {@link}
 *
 * @Desc: 代理类，用于代理被拦截的对象
 * 提供了创建代理类的方法
 * @Author: thy
 * @CreateTime: 2019/5/8
 **/
public class Plugin implements InvocationHandler {
    private Object target;
    private Interceptor interceptor;

    /**
     * @param target      被代理的对象
     * @param interceptor 拦截器
     */
    public Plugin(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    /**
     *
     * @param obj 被代理对象
     * @param interceptor
     * @return
     */
    public static Object wrap(Object obj, Interceptor interceptor) {
        Class<?> clazz = obj.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new Plugin(obj, interceptor));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (interceptor.getClass().isAnnotationPresent(Intercepts.class)) {
            if (method.getName().equals(interceptor.getClass().getAnnotation(Intercepts.class).value())) {
                return interceptor.intercept(new Invocation(target, method, args));
            }
        }
        return method.invoke(target, method, args);
    }
}
