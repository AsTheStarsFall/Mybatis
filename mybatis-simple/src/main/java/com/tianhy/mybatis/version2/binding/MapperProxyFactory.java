package com.tianhy.mybatis.version2.binding;

import com.tianhy.mybatis.version2.session.DefaultSqlSession;

import java.lang.reflect.Proxy;

/**
 * {@link}
 *
 * @Desc: 代理工厂类，用于生产代理类
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
public class MapperProxyFactory<T> {

    /**
     * 接口
     */
    private Class<T> mapperInterface;
    /**
     * 指定了实体类型
     */
    private Class object;

    public MapperProxyFactory(Class<T> mapperInterface, Class object) {
        this.mapperInterface = mapperInterface;
        this.object = object;
    }

    public T newInstance(DefaultSqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, new MapperProxy(sqlSession, object));
    }
}
