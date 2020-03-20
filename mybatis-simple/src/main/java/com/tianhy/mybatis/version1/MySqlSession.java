package com.tianhy.mybatis.version1;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * {@link}
 *
 * @Desc: SQL会话
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
@Slf4j
public class MySqlSession {
    private MyConfiguration configuration;
    private MyExecutor executor;

    public MySqlSession(MyConfiguration configuration, MyExecutor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T getMapper(Class clazz){
        return configuration.getMapper(clazz,this);
    }

    public <T> T selectOne(String statementId,Object parameter){
        //通过与SQL配置文件全限定名匹配，获取对应的SQL语句
        String sql = MyConfiguration.sqlMappings.getString(statementId);
        if(StringUtils.isNotBlank(sql)){
            log.debug(sql);
            return executor.query(sql,parameter);
        }
        return null;
    }
}
