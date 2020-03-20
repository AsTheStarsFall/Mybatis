package com.tianhy.mybatis;

import com.tianhy.mybatis.version2.mapper.Blog;
import com.tianhy.mybatis.version2.mapper.BlogMapper;
import com.tianhy.mybatis.version2.session.DefaultSqlSession;
import com.tianhy.mybatis.version2.session.SqlSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.nutz.json.Json;

import java.util.List;

/**
 * {@link}
 *
 * @Desc:
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
@Slf4j
public class Version2Test {

    public static void main(String[] args) {
        SqlSessionFactory factory = new SqlSessionFactory();
        DefaultSqlSession defaultSqlSession = factory.build().openSession();
        BlogMapper mapper = defaultSqlSession.getMapper(BlogMapper.class);
//        Blog blog = mapper.selectBlogById(1);
//        log.debug("第一次查询 :" + blog);
//        blog = mapper.selectBlogById(1);
//        log.debug("第二次查询 :" + blog);

//        List<Blog> blogs = mapper.selectBlogList();
        List<Blog> blogs = mapper.selectBlogByName("Thy");
        log.debug(blogs.toString());
    }
}
