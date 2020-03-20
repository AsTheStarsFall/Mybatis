package com.tianhy.mybatis.version2.session;

import com.tianhy.mybatis.version2.annotation.Entity;
import com.tianhy.mybatis.version2.executor.*;
import com.tianhy.mybatis.version2.binding.MapperRegistry;
import com.tianhy.mybatis.version2.plugin.Interceptor;
import com.tianhy.mybatis.version2.plugin.InterceptorChain;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * {@link}
 *
 * @Desc: 全局配置类
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
@Slf4j
public class Configuration {

    /**
     * 接口与工厂类映射
     */
    private static final MapperRegistry MAPPER_REGISTRY = new MapperRegistry();
    /**
     * 接口方法与SQL映射
     */
    private static final Map<String, String> MAPPER_STATEMENT = new HashMap<>();

    /**
     * 拦截器链
     */
    private InterceptorChain interceptorChain = new InterceptorChain();

    /**
     * Mapper接口
     */
    private List<Class<?>> mapperList = new ArrayList<>();
    /**
     * mybatis配置
     */
    private static final ResourceBundle MYBATIS_CONFIG;
    private static final String CACHE_ENABLED = "cache.enabled";
    private static final String PLUGIN_PATH = "plugin.path";
    private static final String MAPPER_PATH = "mapper.path";

    /**
     * SQL 配置
     */
    private static final ResourceBundle SQL_MAPPINGS;

    static {
        SQL_MAPPINGS = ResourceBundle.getBundle("sql");
        MYBATIS_CONFIG = ResourceBundle.getBundle("mybatis");
    }

    /**
     * 初始化时，解析全局配置文件
     */
    public Configuration() {
        //1、解析SQL配置文件
        for (String key : SQL_MAPPINGS.keySet()) {
            String statement = SQL_MAPPINGS.getString(key);
            MAPPER_STATEMENT.put(key, statement);
        }
        //2、解析Mapper配置
        String mapperPath = MYBATIS_CONFIG.getString(MAPPER_PATH);
        scanPackage(mapperPath);
        for (Class<?> mapper : mapperList) {
            if (!mapper.isAnnotationPresent(Entity.class)) {
                continue;
            }
            Class<?> value = mapper.getAnnotation(Entity.class).value();
            //注册接口与实体类映射关系
            MAPPER_REGISTRY.addMapper(mapper, value);
        }

        //3、解析插件
        String pluginPath = MYBATIS_CONFIG.getString(PLUGIN_PATH);
        String[] plugins = pluginPath.split(",");
        if (plugins != null) {
            for (String plugin : plugins) {
                Interceptor interceptor = null;
                try {
                    Class<?> clazz = Class.forName(plugin);
                    Object instance = clazz.newInstance();
                    interceptor = (Interceptor) instance;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //此时interceptor 的引用是它的实现类 MyPlugin
                //将拦截的类放入拦截器链
                interceptorChain.addInterceptor(interceptor);
            }
        }


    }

    /**
     * 扫描Mapper接口
     *
     * @param mapperPath
     */
    private void scanPackage(String mapperPath) {
        URL url = this.getClass().getResource("/" + mapperPath.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                scanPackage(mapperPath + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                //去掉.class后缀
                String className = mapperPath + "." + file.getName().replace(".class", "");
                Class<?> clazz = null;
                try {
                    clazz = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    log.error(className + "not found");
                }
                mapperList.add(clazz);
            }
        }

    }

    /**
     * 创建执行器
     * 1、开启缓存开关时使用缓存装饰器查询
     * 2、配置插件时，使用插件代理
     */
    public Executor newExecutor() {
        Executor executor = null;
        //如果开启了缓存
        if (MYBATIS_CONFIG.getString(CACHE_ENABLED).equals("true")) {
            executor = new CashingExecutor(new SimpleExecutor());
        } else {
            executor = new SimpleExecutor();
        }

        //插件只拦截了Executor
        if (interceptorChain.hasPlugin()){
            //对Executor进行代理
            //此时的Executor的引用是它的实现类 CashingExecutor 或者 SimpleExecutor
            //对其进行代理
            return (Executor) interceptorChain.pluginAll(executor);
        }
        return executor;
    }

    /**
     * 创建代理对象
     *
     * @param clazz
     * @param sqlSession
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<T> clazz, DefaultSqlSession sqlSession) {
        return MAPPER_REGISTRY.getMapper(clazz, sqlSession);
    }

    /**
     * statement与SQL匹配
     *
     * @param statementId 接口+方法全限名称
     */
    public boolean hasStatement(String statementId) {
        return MAPPER_STATEMENT.containsKey(statementId);
    }

    /**
     * 根据 statementId 获取SQL语句
     *
     * @param statementId
     */
    public String getMapperStatement(String statementId) {
        return MAPPER_STATEMENT.get(statementId);
    }

}
