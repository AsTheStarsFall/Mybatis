package com.tianhy.mybatis.version1;


import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * {@link}
 *
 * @Desc:
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
@Slf4j
public class MyMapperProxy implements InvocationHandler {
    private MySqlSession sqlSession;

    public MyMapperProxy(MySqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String a = method.getDeclaringClass().getName();
        String b = method.getName();
        // 全限名称
        String c = a + "." + b;
//        log.debug(c);
//        log.debug("参数：" + args[0]);
        return sqlSession.selectOne(c,args[0]);
    }
}
