package com.tianhy.mybatis.version2.binding;

import com.tianhy.mybatis.version2.annotation.ResultType;
import com.tianhy.mybatis.version2.session.DefaultSqlSession;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * {@link}
 *
 * @Desc: 代理类，用于代理Mapper接口
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
@Slf4j
public class MapperProxy implements InvocationHandler {

    private DefaultSqlSession sqlSession;
    private Class object;

    public MapperProxy(DefaultSqlSession sqlSession, Class object) {
        this.sqlSession = sqlSession;
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String a = method.getDeclaringClass().getName();
        String methodName = method.getName();
        //全限定名称
        String statementId = a + "." + methodName;
        //如果statementId与SQL能匹配上
        if (sqlSession.getConfiguration().hasStatement(statementId)) {
            if (method.getReturnType().getName().equals(List.class.getName())) {
                return sqlSession.selectList(statementId, object, args);
            }
            return sqlSession.selectOne(statementId, object, args);
        }
        return method.invoke(proxy, args);
    }
}
