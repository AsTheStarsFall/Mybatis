package com.tianhy.mybatis.version2.mapper;

import lombok.Data;

import javax.persistence.*;

/**
 * {@link}
 *
 * @Desc:
 * @Author: thy
 * @CreateTime: 2019/4/22
 **/
@Entity
@Table(name = "user")
@Data
public class User {

    @Id private int id;

    @Column(name = "user_name")
    private String name;

    private int age;
    private String password;
}
