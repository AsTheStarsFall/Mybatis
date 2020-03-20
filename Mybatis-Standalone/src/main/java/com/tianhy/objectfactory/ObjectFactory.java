package com.tianhy.objectfactory;

import com.tianhy.domain.Blog;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

/**
 * {@link}
 *
 * @Desc:
 * @Author: thy
 * @CreateTime: 2019/4/27
 **/
@Slf4j
public class ObjectFactory extends DefaultObjectFactory {

    @Override
    public Object create(Class type) {
        log.debug("创建方法 : "+ type);
        if(type.equals(Blog.class)){
            Blog blog = (Blog) super.create(type);
            blog.setName("object factory");
            return blog;
        }
        Object object = super.create(type);
        return object;
    }
}
