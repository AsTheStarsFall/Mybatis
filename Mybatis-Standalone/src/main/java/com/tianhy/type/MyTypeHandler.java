package com.tianhy.type;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.*;

import java.sql.*;

/**
 * {@link}
 *
 * @Desc: 从Java类型到JDBC类型， 从JDBC类型到Java类型
 * @Author: thy
 * @CreateTime: 2019/4/26
 **/
@Slf4j
public class MyTypeHandler extends BaseTypeHandler {
    //@MappedJdbcTypes(JdbcType.VARCHAR) 可以选择性的映射JDBC type

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        log.debug("------parameter : " + parameter + ", type : " + jdbcType);
        ps.setObject(i, parameter);
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        log.debug("------getNullableResult : " + rs.getObject(columnName));
        return rs.getObject(columnName);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        log.debug("------getNullableResult : " + rs.getObject(columnIndex));
        return rs.getObject(columnIndex);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        log.debug("------getNullableResult : " + cs.getObject(columnIndex));
        return cs.getObject(columnIndex);
    }
}
