package com.tianhy;

import com.google.gson.Gson;
import com.tianhy.domain.Blog;
import com.tianhy.domain.associate.*;
import com.tianhy.mapper.BlogMapper;
import com.tianhy.mapper.BlogMapperExt;
import com.tianhy.result.MyResultHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * {@link}
 *
 * @Desc:
 * @Author: thy
 * @CreateTime: 2019/4/26
 **/
@Slf4j
public class MybatisTest {
    private static Gson gson = new Gson();

    @Test
    public void testMybatis() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        Blog blog = sqlSession.selectOne("com.tianhy.mapper.BlogMapper.selectBlogById", 1);
        log.debug(gson.toJson(blog));
        sqlSession.close();
    }


    //加载配置文件
    @Test
    public void properties() throws IOException {
        String resource = "mybatis-config.xml";
        String properties = "db.properties";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        Properties pro = new Properties();
        pro.load(resourceAsStream);

//        pro.loadFromXML(resourceAsStream);

    }

    // getMapper()
    @Test
    public void testMybatis1() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

//        //映射
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = new Blog();
//            blog.setBid(new Random().nextInt());
        blog.setName("Thy");
//        Blog blog = blogMapper.selectBlogById(1);
        List<Blog> blogs = blogMapper.selectBlogByBean(blog);

//
        log.debug(gson.toJson(blogs));

        sqlSession.close();

    }

    //对结果集进行处理
    @Test
    public void tesResultHandler() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //处理集结果集的类
        MyResultHandler handler = new MyResultHandler();
        sqlSession.select("com.tianhy.mapper.BlogMapper.selectBlogById", 1, handler);

        //获取处理后的map
        Map mappedResults = handler.getMappedResults();

        log.debug("selectBlogById : " + gson.toJson(mappedResults));

        sqlSession.close();

    }

    @Test
    public void testInstert() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            //映射
            BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
            Blog blog = new Blog();
//            blog.setBid(new Random().nextInt());
            blog.setName("Thy");
            blog.setAuthorId(6666);
            log.debug(String.valueOf(blogMapper.insertBlog(blog)));
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    // ${}、#{} 的区别
    @Test
    public void testSelectByBean() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            //映射
            BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
            Blog queryBean = new Blog();
            queryBean.setName("Thy");
            // $ # 的区别：#{} 将替换为 ? ,PreparedStatement的set()方法赋值
            //            ${} 将替换为变量的值
            List<Blog> blogs = blogMapper.selectBlogByBean(queryBean);
            log.debug(gson.toJson(blogs));
        } finally {
            sqlSession.close();
        }
    }

    // 逻辑分页
    @Test
    public void selectByRoundBounds() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            //映射
            BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
            int start = 0;
            int pageSize = 5;
            //从start+1开始取pageSize条记录
            RowBounds rowBounds = new RowBounds(start, pageSize);
            List<Blog> blogs = blogMapper.selectBlogList(rowBounds);
            log.debug(gson.toJson(blogs));
        } finally {
            sqlSession.close();
        }
    }

    //Mapper.xml的继承性
    @Test
    public void testMapperExt() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            //映射
            BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
            Blog blog = blogMapper.selectBlogById(1);

            BlogMapperExt blogMapperExt = sqlSession.getMapper(BlogMapperExt.class);
            Blog blog1 = blogMapperExt.selectBlogByName("Thy");

            log.debug(gson.toJson(blog));
            log.debug(gson.toJson(blog1));

        } finally {
            sqlSession.close();
        }
    }

    //一对一嵌套查询，一篇文章对应一个作者 不存在N+1的问题,在映射器中是嵌套查询
    @Test
    public void testSelectBlogWithAuthorResult() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            //映射
            BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
            BlogAndAuthor blogAndAuthor = blogMapper.selectBlogWithAuthorResult(1);

            log.debug(blogAndAuthor.toString());
        } finally {
            sqlSession.close();
        }

    }


    //一对一嵌套查询，一篇文章对应一个作者 因为查询操作被代理了所以会有N+1的问题，可通过开启延时加载解决，在映射器中是联合查询
    @Test
    public void testSelectBlogWithAuthorQuery() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            //映射
            BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
            BlogAndAuthor blogAndAuthor = blogMapper.selectBlogWithAuthorQuery(1);
            log.debug("------------" + blogAndAuthor.getClass());
            //如果 lazyLoadingEnabled = true ,只有在使用的时候会触发SQL
            // equals,clone,hashCode,toString也会触发懒加载
//            log.debug(blogAndAuthor.getAuthor().toString());

            //aggressiveLazyLoading 强制懒加载,不管用不用都触发SQL
            //如果 aggressiveLazyLoading = true，也会触发懒加载
//            log.debug("-----------"+blogAndAuthor.getAuthor().getClass());
//            log.debug(blogAndAuthor.getName());
        } finally {
            sqlSession.close();
        }
    }


    //一对多，一篇文章对应多个评论
    @Test
    public void testSelectBlogWithCommentById() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            //映射
            BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
            BlogAndComment blogAndComment = blogMapper.selectBlogWithCommentById(1);

            log.debug(blogAndComment.toString());
//            log.debug(gson.toJson(blogAndComment.getComment()));
        } finally {
            sqlSession.close();
        }
    }

    //多对多，作者的文章评论
    @Test
    public void testSelectAuthorWithBlog() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            //映射
            BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
            List<AuthorAndBlog> authorAndBlogs = blogMapper.selectAuthorWithBlog();

            log.debug(authorAndBlogs.toString());
        } finally {
            sqlSession.close();
        }
    }


}
