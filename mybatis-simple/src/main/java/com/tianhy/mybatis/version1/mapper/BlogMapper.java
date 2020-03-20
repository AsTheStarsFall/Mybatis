package com.tianhy.mybatis.version1.mapper;

import com.tianhy.mybatis.version1.domain.Blog;

/**
 * @Description:
 * @Author: thy
 * @Date: 2019/4/26
 */
public interface BlogMapper {
    /**
     * 根据主键查询文章
     *
     * @param bid
     * @return
     */
    public Blog selectBlogById(Integer bid);

}
