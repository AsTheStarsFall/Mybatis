package com.tianhy.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;
import java.util.Properties;

/**
 * {@link}
 *
 * @Desc: 拦截StatementHandler，获取SQL信息
 * @Author: thy
 * @CreateTime: 2019/5/2
 **/
@Intercepts({@Signature(type = StatementHandler.class,method = "query",args = {
        Statement.class, ResultHandler.class
})})
@Slf4j
public class SqlRecordInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        StatementHandler sm = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = sm.getBoundSql();
        String sql = boundSql.getSql();
        log.debug("获取SQL语句 ： \n"+ sql);

        try {
            return invocation.proceed();

        }finally {
            long end = System.currentTimeMillis();
            log.debug("sql 耗时 ：" + (end - start) + "ms");
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
