package com.tianhy.mybatis.version2.mapper;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description: Blog实体类
 * @Author: thy
 * @Date: 2019/4/26
 */
@Entity
@Table(name = "blog")
public class Blog implements Serializable {
    /**
     * 文章ID
     */
    @Id
    Integer bid;
    /**
     * 文章标题
     */
    String name;

    /**
     * 文章作者ID
     */
    @Column(name = "author_id")
    Integer authorId;

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "bid=" + bid +
                ", name='" + name + '\'' +
                ", authorId='" + authorId + '\'' +
                '}';
    }
}
