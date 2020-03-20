package com.tianhy.domain.associate;


import com.tianhy.domain.Author;

import java.io.Serializable;

/**
 * @Description: 根据文章查作者1：1
 * @Author: thy
 * @Date: 2019/4/26
 */
public class BlogAndAuthor implements Serializable {
    Integer bid; // 文章ID
    String name; // 文章标题
    Author author; // 作者

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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "BlogAndAuthor{" +
                "bid=" + bid +
                ", name='" + name + '\'' +
                ", author=" + author +
                '}';
    }
}
