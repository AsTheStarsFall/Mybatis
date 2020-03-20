package com.tianhy.mybatis.version2.executor;

import java.util.List;

/**
 * {@link}
 *
 * @Desc: 数据库操作执行器接口
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
public interface Executor {

    /**
     * @param statement SQL语句
     * @param pojo      实体对象
     * @param parameter 参数
     * @param <T>
     * @return
     */
    <T> List<T> query(String statement, Class pojo, Object... parameter);
}
