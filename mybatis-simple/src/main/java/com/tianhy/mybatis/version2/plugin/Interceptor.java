package com.tianhy.mybatis.version2.plugin;

/**
 * {@link}
 *
 * @Desc: 拦截器接口
 * @Author: thy
 * @CreateTime: 2019/5/8
 **/
public interface Interceptor {

    Object intercept(Invocation invocation) throws Throwable;

    /**
     * 对被拦截的对象进行代理
     * @param target
     * @return
     */
    Object plugin(Object target);
}
