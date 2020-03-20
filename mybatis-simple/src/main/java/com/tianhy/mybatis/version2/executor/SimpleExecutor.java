package com.tianhy.mybatis.version2.executor;

import java.util.List;

/**
 * {@link}
 *
 * @Desc: 基本的执行器
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
public class SimpleExecutor implements Executor{
    @Override
    public <T> List<T> query(String statement, Class pojo, Object... parameter) {
        StatementHandler statementHandler = new StatementHandler();
        return statementHandler.query(statement,pojo,parameter);
    }
}
