package com.tianhy.mybatis.version2.executor;

import com.tianhy.mybatis.version2.cache.CacheKey;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * {@link}
 *
 * @Desc: 带缓存的执行器，装饰基本的执行器
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
@Slf4j
public class CashingExecutor implements Executor {

    private Executor delegate;
    /**
     * 缓存
     */
    private static final Map<Integer, Object> CACHE = new HashMap<>();

    public CashingExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T> List<T> query(String statement, Class pojo, Object... parameter) {
        //计算缓存是否命中
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(parameter);
        cacheKey.update(joinStr(parameter));

//        log.debug(Arrays.toString(parameter));

//        log.debug(gson.toJson(CACHE));
//        log.debug(String.format("%d",cacheKey.getHashcode()));
        //如果缓存命中
        if (CACHE.containsKey(cacheKey.getHashcode())) {
            System.out.println("缓存命中");
            return (List<T>) CACHE.get(cacheKey.getHashcode());
        } else {
            //如果缓存未命中，查询并将其缓存
            Object query = delegate.query(statement, pojo, parameter);
            CACHE.put(cacheKey.getHashcode(), query);
            return (List<T>) query;
        }
    }


    /**
     * 为了命中缓存，把Object[]转换成逗号拼接的字符串，因为对象的HashCode都不一样
     */
    public String joinStr(Object[] objs) {
        StringBuffer sb = new StringBuffer();
        if (objs != null && objs.length > 0) {
            for (Object objStr : objs) {
                sb.append(objStr.toString() + ",");
            }
        }
        int len = sb.length();
        if (len > 0) {
            sb.deleteCharAt(len - 1);
        }
        return sb.toString();
    }
}
