package com.tianhy;

import com.tianhy.domain.Blog;
import org.junit.Test;

import java.sql.*;

/**
 * {@link}
 *
 * @Desc: 原生JDBC的方式
 * @Author: thy
 * @CreateTime: 2019/4/26
 **/
public class JDBCTest {

    @Test
    public void testJDBC() {
        Connection conn = null;
        Statement stmt = null;
        Blog blog = new Blog();

        try {
            //注册JDBC驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis", "root", "root");
            //创建语句集
            stmt = conn.createStatement();
            String sql = "SELECT bid,name,author_id FROM blog";
            //获取结果集
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                blog.setBid(rs.getInt("bid"));
                blog.setName(rs.getString("name"));
                blog.setAuthorId(rs.getInt("author_id"));
            }

            System.out.println(blog);
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

    }

    @Test
    public void testJDBCBatch() {
        //JDBC批量操作
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis", "root", "root");
            //创建语句集
            String sql = "INSERT INTO blog VALUES(?,?,?)";
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < 10; i++) {
                ps.setInt(1, i);
                ps.setString(2, "name-" + i);
                ps.setInt(3, 1000 + i);
                //批量操作
                ps.addBatch();
            }
            //执行语句集
            ps.executeBatch();
            //conn.commit();
            conn.close();
            ps.close();
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
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
