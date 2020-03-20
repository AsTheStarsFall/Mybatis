package com.tianhy.mybatis.version2.executor;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.*;

/**
 * {@link}
 *
 * @Desc: 结果集处理器
 * @Author: thy
 * @CreateTime: 2019/5/7
 **/
public class ResultSetHandler<T> {


    public <T>List<T> handler(ResultSet rs, Class pojo) {
        Map<String, String> columnMapper = new HashMap<>();
        Map<String, String> fieldMapper = new HashMap<>();

        List resultList = new ArrayList();
        try {
            Field[] fields = pojo.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    String columnName = column.name();
                    columnMapper.put(columnName, fieldName);
                    fieldMapper.put(fieldName, columnName);
                } else {
                    //默认字段名与属性名一致
                    columnMapper.put(fieldName, fieldName);
                    fieldMapper.put(fieldName, fieldName);
                }
            }
            int columnCounts = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object instance = pojo.newInstance();
                //从1开始
                for (int i = 1; i <= columnCounts; i++) {
                    //从结果集获取字段名
                    String columnName = rs.getMetaData().getColumnName(i);
                    //获取字段
                    Field field = pojo.getDeclaredField(columnMapper.get(columnName));
                    field.setAccessible(true);
                    field.set(instance, rs.getObject(columnName));
                }
                resultList.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
