package com.tianhy.mybatis.version2.parameter;

import java.sql.PreparedStatement;

/**
 * {@link}
 *
 * @Desc:
 * @Author: thy
 * @CreateTime: 2019/5/8
 **/
public class ParameterHandler {

    private PreparedStatement psmt;

    public ParameterHandler(PreparedStatement preparedStatement) {
        this.psmt = preparedStatement;
    }

    /**
     * 设置参数，SQL中的占位符 ?
     *
     * @param parameters
     */
    public void setParameters(Object... parameters) {
        int k = 0;
        try {
            for (int i = 0; i < parameters.length; i++) {
                //PreparedStatement 序号从1开始
                ++k;
                if (parameters[i] instanceof Integer) {
                    psmt.setInt(k, (Integer) parameters[i]);
                } else if (parameters[i] instanceof Long) {
                    psmt.setLong(k, (Long) parameters[i]);
                } else if (parameters[i] instanceof String) {
                    psmt.setString(k, String.valueOf(parameters[i]));
                } else if (parameters[i] instanceof Boolean) {
                    psmt.setBoolean(k, (Boolean) parameters[i]);
                } else {
                    psmt.setString(k, String.valueOf(parameters[i]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
