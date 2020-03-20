package com.tianhy.mybatis.version2.plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link}
 *
 * @Desc: 拦截器链
 * @Author: thy
 * @CreateTime: 2019/5/8
 **/
public class InterceptorChain {
    private final List<Interceptor> interceptors = new ArrayList<>();

    /**
     * 添加拦截器到拦截器链中
     *
     * @param interceptor
     */
    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    /**
     * 对被拦截对象进行代理
     * 在Configuration中创建执行器Executor的时候调用
     *
     * @param target Executor接口
     * @return
     */
    public Object pluginAll(Object target) {
        for (Interceptor interceptor : interceptors) {
            target = interceptor.plugin(target);
        }
        return target;
    }

    public boolean hasPlugin() {
        if (interceptors.size() == 0) {
            return false;
        }
        return true;
    }
}
