package com.tianhy.mybatis.version2.executor;

import com.tianhy.mybatis.version2.parameter.ParameterHandler;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

/**
 * {@link ResultSetHandler}
 *
 * @Desc: 封装JDBC Statement，用于操作数据库
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
@Slf4j
public class StatementHandler {
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc");
    private String driver = resourceBundle.getString("jdbc.driver");
    private String url = resourceBundle.getString("jdbc.url");
    private String userName = resourceBundle.getString("jdbc.username");
    private String passWord = resourceBundle.getString("jdbc.password");

    /**
     * 结果集处理器
     */
    private ResultSetHandler resultSetHandler = new ResultSetHandler();

    public <T> List<T> query(String statement, Class pojo, Object[] parameter) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = getConnection();
            preparedStatement = conn.prepareStatement(statement);
            //入参处理
            ParameterHandler parameterHandler = new ParameterHandler(preparedStatement);
            if(parameter != null){
                parameterHandler.setParameters(parameter);
            }
            log.debug(preparedStatement.toString().split(":")[1]);
            preparedStatement.execute();
            return resultSetHandler.handler(preparedStatement.getResultSet(), pojo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                conn = null;
            }
        }
        return null;
    }

    private Connection getConnection() {
        Connection conn = null;
        try {
            //注册JDBC驱动
            Class.forName(driver);
            //获取连接
            conn = DriverManager.getConnection(url, userName, passWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
