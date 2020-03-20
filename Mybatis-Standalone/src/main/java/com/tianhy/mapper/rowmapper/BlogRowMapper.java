package com.tianhy.mapper.rowmapper;


import com.tianhy.domain.Blog;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link}
 *
 * @Desc:
 * @Author: thy
 * @CreateTime: 2019/4/26
 **/
public class BlogRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Blog blog = new Blog();
        blog.setBid(rs.getInt("bid"));
        blog.setName(rs.getString("name"));
        blog.setAuthorId(rs.getInt("author_id"));
        return blog;
    }
}
