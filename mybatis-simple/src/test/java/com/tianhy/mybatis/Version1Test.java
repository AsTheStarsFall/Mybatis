package com.tianhy.mybatis;

import com.tianhy.mybatis.version1.*;
import com.tianhy.mybatis.version1.domain.Blog;
import com.tianhy.mybatis.version1.mapper.BlogMapper;

/**
 * {@link}
 *
 * @Desc:
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
public class Version1Test {

    public static void main(String[] args) {
        MySqlSession sqlSession = new MySqlSession(new MyConfiguration(),new MyExecutor());
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = mapper.selectBlogById(1);
    }
}

