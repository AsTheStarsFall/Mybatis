package com.tianhy.mybatis.version1;


import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

/**
 * {@link}
 *
 * @Desc:
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
public class MyConfiguration {

    // SQL配置文件内容
    public static final ResourceBundle sqlMappings;

    static {
        sqlMappings = ResourceBundle.getBundle("sql");
    }

    public <T> T getMapper(Class clazz,MySqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new MyMapperProxy(sqlSession));
    }
}
