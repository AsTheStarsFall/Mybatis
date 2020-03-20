package com.tianhy.dbutils;

import java.sql.SQLException;

/**
 * {@link}
 *
 * @Desc:
 * @Author: thy
 * @CreateTime: 2019/4/26
 **/
public class Main {

    public static void main(String[] args) throws SQLException {
        HikariUtil.init();
        BlogDao.selectById(1);
        BlogDao.selectList();
    }
}
