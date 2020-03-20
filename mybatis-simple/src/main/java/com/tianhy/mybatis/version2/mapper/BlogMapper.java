package com.tianhy.mybatis.version2.mapper;

import com.tianhy.mybatis.version2.annotation.Entity;

import java.util.List;

/**
 * @Description:
 * @Author: thy
 * @Date: 2019/4/26
 */
@Entity(Blog.class)
public interface BlogMapper {
    /**
     * 根据主键查询文章
     *
     * @param bid
     * @return
     */
    public Blog selectBlogById(Integer bid);

    public List<Blog> selectBlogByName(String name);

    public List<Blog> selectBlogList();
}
