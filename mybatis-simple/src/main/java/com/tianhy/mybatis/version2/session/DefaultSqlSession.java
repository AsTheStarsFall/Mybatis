package com.tianhy.mybatis.version2.session;

import com.tianhy.mybatis.version2.executor.Executor;

import java.util.List;

/**
 * {@link Configuration,Executor}
 *
 * @Desc: Mybatis API，供应用层使用
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
public class DefaultSqlSession {

    private Configuration configuration;
    private Executor executor;


    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = configuration.newExecutor();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * 创建代理对象
     * @param clazz
     * @param <T>
     */
    public <T> T getMapper(Class<T> clazz) {
        return configuration.getMapper(clazz, this);
    }

    /**
     * @param statementId
     * @param pojo
     * @param parameter
     * @param <T>
     */
    public <T> T selectOne(String statementId,Class pojo,Object... parameter){
        String sql = getConfiguration().getMapperStatement(statementId);
        List<T> result = executor.query(sql,pojo,parameter);
        if(result.size() == 1){
            return result.get(0);
        }else if(result.size()> 1){
            throw new IllegalArgumentException("result size > 1");
        }else {
            return null;
        }
    }
    public <T> List<T> selectList(String statementId,Class pojo,Object... parameter){
        String sql = getConfiguration().getMapperStatement(statementId);
        return executor.query(sql,pojo,parameter);
    }

}
