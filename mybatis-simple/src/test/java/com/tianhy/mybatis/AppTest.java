package com.tianhy.mybatis;


import com.tianhy.mybatis.version2.annotation.Entity;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        try {
            Class<?> blogMapper = Class.forName("com.tianhy.mybatis.version2.mapper.BlogMapper");
            Method[] methods = blogMapper.getMethods();
            for (Method method : methods) {
                if(method.isAnnotationPresent(Entity.class)){
                    String typeName = Entity.class.getTypeName();
                    System.out.println(typeName);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSqlProperties(){

    }
}
