package com.tianhy.mybatis.version2.binding;

import com.tianhy.mybatis.version2.session.DefaultSqlSession;
import com.tianhy.mybatis.version2.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link MapperProxyFactory,DefaultSqlSession}
 *
 * @Desc: 维护接口与工厂类的关系，获取mapperProxy代理对象
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
public class MapperRegistry {

    /**
     * 接口与工厂映射关系
     */
    private final Map<Class<?>, MapperProxyFactory> knowMappers = new HashMap<>();


    /**
     * 解析Configuration时，通过扫描SQL配置文件或者注解，将接口与实体映射
     * 然后通过 knowMappers 将接口与代理工厂类映射
     * @param clazz 接口
     * @param pojo 实体类型
     * @param <T>
     */
    public <T> T addMapper(Class<T> clazz,Class pojo){
        return (T) knowMappers.put(clazz,new MapperProxyFactory(clazz,pojo));
    }


    /**
     * 创建代理对象
     *
     * @param clazz
     * @param sqlSession
     * @param <T>
     */
    public <T> T getMapper(Class<T> clazz, DefaultSqlSession sqlSession) {
        MapperProxyFactory factory = knowMappers.get(clazz);
        return (T) factory.newInstance(sqlSession);
    }
}
