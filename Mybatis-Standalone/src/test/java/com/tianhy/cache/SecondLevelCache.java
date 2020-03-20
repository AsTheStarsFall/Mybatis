package com.tianhy.cache;

import com.google.gson.Gson;
import com.tianhy.domain.Blog;
import com.tianhy.mapper.BlogMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * {@link}
 *
 * @Desc: 二级缓存解决了一级缓存不能跨会话共享的问题，范围是namespace级别的
 * @Author: thy
 * @CreateTime: 2019/5/1
 **/
@Slf4j
public class SecondLevelCache {
    private static Gson gson = new Gson();

    @Test
    public void testMybatis() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SqlSession sqlSession1 = sqlSessionFactory.openSession();

        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        log.debug("第一次查询");
        Blog blog = blogMapper.selectBlogById(1);
        log.debug(gson.toJson(blog));
        //如果不提交事务，就不会写入缓存
        sqlSession.commit();

        BlogMapper blogMapper1 = sqlSession1.getMapper(BlogMapper.class);
        log.debug("第二次查询");
        Blog blog1 = blogMapper1.selectBlogById(1);
        log.debug(gson.toJson(blog1));

        sqlSession.close();
        sqlSession1.close();
    }

    @Test
    public void testCacheInvalid() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session1 = sqlSessionFactory.openSession();
        SqlSession session2 = sqlSessionFactory.openSession();
        SqlSession session3 = sqlSessionFactory.openSession();
        try {
            BlogMapper mapper1 = session1.getMapper(BlogMapper.class);
            BlogMapper mapper2 = session2.getMapper(BlogMapper.class);
            BlogMapper mapper3 = session3.getMapper(BlogMapper.class);
            log.debug("第一次查询,commit()写入缓存");
            log.debug(gson.toJson(mapper1.selectBlogById(1)));
            session1.commit();

            // 是否命中二级缓存
            log.debug("是否命中二级缓存？");
            log.debug(gson.toJson(mapper2.selectBlogById(1)));

            Blog blog = new Blog();
            blog.setBid(1);
            blog.setName("2019年1月6日15:03:38");
            mapper3.updateByPrimaryKey(blog);
            session3.commit();

            //所有的增删改查都会刷新二级缓存
           log.debug("更新后再次查询，是否命中二级缓存？");
            // 在其他会话中执行了更新操作，二级缓存是否被清空？
            log.debug(gson.toJson(mapper2.selectBlogById(1)));

        } finally {
            session1.close();
            session2.close();
            session3.close();
        }
    }
}
