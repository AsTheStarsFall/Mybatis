package com.tianhy.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * {@link}
 *
 * @Desc: 拦截Executor对象，修改分页处理
 * @Author: thy
 * @CreateTime: 2019/5/2
 **/
//指定拦截的对象和方法，方法参数
@Intercepts({@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
@Slf4j
public class MyPageInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.debug("逻辑分页改为物理分页");
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];

        //An actual SQL String got from an SqlSource after having processed any dynamic content
        //已经处理过动态内容的BoundSql
        BoundSql boundSql = ms.getBoundSql(args[1]);
        RowBounds rb = (RowBounds) args[2];

        if (rb == RowBounds.DEFAULT) {
            return invocation.proceed();
        }

        String sql = boundSql.getSql();
        log.debug("逻辑分页SQL : \n"+ sql);
        String limit = String.format("LIMIT %d,%d", rb.getOffset(), rb.getLimit());
        sql = sql + " " + limit;
        log.debug("物理分页SQL ：\n"+ sql);
        //自定义SqlSource
        SqlSource sqlSource = new StaticSqlSource(ms.getConfiguration(), sql, boundSql.getParameterMappings());
        //修改原来的SqlSource
        Field field = MappedStatement.class.getDeclaredField("sqlSource");
        field.setAccessible(true);
        field.set(ms, sqlSource);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String tianhy = properties.getProperty("tianhy");
        log.debug("获得properties属性 : "+tianhy);
    }
}
