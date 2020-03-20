package com.tianhy.dbutils;


import com.google.gson.Gson;
import com.tianhy.domain.Blog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * {@link}
 *
 * @Desc:
 * @Author: thy
 * @CreateTime: 2019/4/26
 **/
@Slf4j
public class BlogDao {

    private static Gson json = new Gson();

    //单例
    private static QueryRunner queryRunner;

    static {
        queryRunner = HikariUtil.getQueryRunner();
    }

    public static void selectById(Integer id) throws SQLException {
        String sql = "SELECT * FROM blog where bid = ? ";
        Object[] params = new Object[]{id};
        Blog blog = queryRunner.query(sql, new BeanHandler<>(Blog.class), params);
        log.debug(blog.toString());
    }

    public static void selectList() throws SQLException {
        String sql = "SELECT * FROM blog";
        List<Blog> blogs = queryRunner.query(sql, new BeanListHandler<>(Blog.class));
        log.debug(json.toJson(blogs));
    }
}
