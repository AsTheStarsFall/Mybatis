package com.tianhy.cache;

import com.google.gson.Gson;
import com.tianhy.domain.Blog;
import com.tianhy.mapper.BlogMapper;
import com.tianhy.mapper.BlogMapperExt;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;


/**
 * {@link}
 *
 * @Desc: 一级缓存
 * @Author: thy
 * @CreateTime: 2019/5/1
 **/
@Slf4j
public class FirstLevelCache {
    private static Gson gson = new Gson();


    //一级缓存跨会话缓存不共享
    @Test
    public void test1() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        SqlSession sqlSession0 = sqlSessionFactory.openSession();
        SqlSession sqlSession1 = sqlSessionFactory.openSession();

        try {
            BlogMapper mapper1 = sqlSession0.getMapper(BlogMapper.class);
            BlogMapper mapper2 = sqlSession0.getMapper(BlogMapper.class);

            log.debug("第一次查询");
            log.debug(gson.toJson(mapper1.selectBlogById(1)));

            log.debug("第二次查询相同session :");
            log.debug(gson.toJson(mapper2.selectBlogById(1)));

            log.debug("第三次查询不同session :");
            BlogMapper mapper3 = sqlSession1.getMapper(BlogMapper.class);
            log.debug(gson.toJson(mapper3.selectBlogById(1)));

        }finally {
            sqlSession0.close();
            sqlSession1.close();
        }
    }

    //一级缓存失效
    @Test
    public void test2() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        SqlSession sqlSession0 = sqlSessionFactory.openSession();

        try {
            BlogMapperExt mapper1 = sqlSession0.getMapper(BlogMapperExt.class);

            //update/delete操作会清空缓存
            log.debug("更新前 ： "+gson.toJson(mapper1.selectBlogById(1)));
            Blog blog = new Blog();
            blog.setBid(1);
            blog.setName("1111");
            mapper1.updateByPrimaryKey(blog);
            sqlSession0.commit();
            log.debug("更新后 ： "+gson.toJson(mapper1.selectBlogById(1)));


        }finally {
            sqlSession0.close();
        }
    }


    //脏读
    @Test
    public void testDirtyRead() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session1 = sqlSessionFactory.openSession();
        SqlSession session2 = sqlSessionFactory.openSession();
        try {
            BlogMapper mapper1 = session1.getMapper(BlogMapper.class);
            log.debug("更新前 ： "+gson.toJson(mapper1.selectBlogById(1)));

            // 会话2更新了数据，会话2的一级缓存更新
            Blog blog = new Blog();
            blog.setBid(1);
            blog.setName("after modified +++++++++");
            BlogMapper mapper2 = session2.getMapper(BlogMapper.class);
            mapper2.updateByPrimaryKey(blog);
            session2.commit();

            // 其他会话更新了数据，本会话的一级缓存还在么？
            log.debug("更新后 ： "+gson.toJson(mapper1.selectBlogById(1)));
        } finally {
            session1.close();
            session2.close();
        }
    }
}
