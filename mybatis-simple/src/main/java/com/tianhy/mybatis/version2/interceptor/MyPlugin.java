package com.tianhy.mybatis.version2.interceptor;

import com.tianhy.mybatis.version2.annotation.Intercepts;
import com.tianhy.mybatis.version2.plugin.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * {@link}
 *
 * @Desc: 自定义插件
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
@Slf4j
@Intercepts("query")
public class MyPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable{
        // Executor.query() 的3个参数
        String statement = (String) invocation.getArgs()[0];
        Object[] parameters = (Object[]) invocation.getArgs()[2];
        log.debug("插件输出SQL ："+ statement);
        log.debug("插件输出参数 ："+ Arrays.toString(parameters));
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }
}
