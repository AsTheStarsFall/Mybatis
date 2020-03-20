package com.tianhy.mapper;

import com.tianhy.domain.Blog;

/**
 * {@link}
 *
 * @Desc:
 * @Author: thy
 * @CreateTime: 2019/4/26
 **/
public interface BlogMapperExt extends BlogMapper {

    Blog selectBlogByName(String name);

    @Override
    int updateByPrimaryKey(Blog blog);
}
