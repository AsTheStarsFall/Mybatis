package com.tianhy.domain.associate;


import com.tianhy.domain.Comment;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: thy
 * @Date: 2019/4/26
 */
public class BlogAndComment implements Serializable {
    Integer bid; // 文章ID
    String name; // 文章标题
    Integer authorId; // 文章作者ID
    List<Comment> comment; // 文章评论

    @Override
    public String toString() {
        return "BlogAndComment{" +
                "bid=" + bid +
                ", name='" + name + '\'' +
                ", authorId=" + authorId +
                ", comment=" + comment +
                '}';
    }

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

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
}