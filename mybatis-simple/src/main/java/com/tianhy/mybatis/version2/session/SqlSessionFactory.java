package com.tianhy.mybatis.version2.session;

/**
 * {@link Configuration,DefaultSqlSession}
 *
 * @Desc: 会话工厂，解析配置文件，创建sqlsession
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
public class SqlSessionFactory {

    private Configuration configuration;

    /**
     * 初始化Configuration，在其构造函数中解析
     */
    public SqlSessionFactory build() {
        configuration = new Configuration();
        return this;
    }

    /**
     * 获取DefaultSqlSession
     */
    public DefaultSqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
