package com.tianhy.mybatis.version1;

import com.tianhy.mybatis.version1.domain.Blog;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * {@link}
 *
 * @Desc: 操作数据库执行器
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
@Slf4j
public class MyExecutor {
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc");
    private String driver = resourceBundle.getString("jdbc.driver");
    private String url = resourceBundle.getString("jdbc.url");
    private String userName = resourceBundle.getString("jdbc.username");
    private String passWord = resourceBundle.getString("jdbc.password");

    public <T> T query(String sql,Object parameter){
        Connection conn = null;
        Statement stmt = null;
        Blog blog = new Blog();

        try {
            //注册JDBC驱动
            Class.forName(driver);
            //获取连接
            conn = DriverManager.getConnection(url, userName, passWord);
            //创建语句集
            stmt = conn.createStatement();
//            String sql = "select * from blog where bid = ?";

//            log.debug("sql = "+ sql);
//            log.debug("parameter = "+ parameter);
            // SQL配置文件中是用 %d 占位符
            log.debug(String.format(sql,parameter));
            // ${} 直接拼接成SQL语句，statement
            // #{} 与SQL进行拼接,prestatement
            //获取结果集
            ResultSet rs = stmt.executeQuery(String.format(sql,parameter));
            while (rs.next()) {
                blog.setBid(rs.getInt("bid"));
                blog.setName(rs.getString("name"));
                blog.setAuthorId(rs.getInt("author_id"));
            }
            log.debug(blog.toString());
            rs.close();
            conn.close();
            stmt.close();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return (T) blog;
    }
}
