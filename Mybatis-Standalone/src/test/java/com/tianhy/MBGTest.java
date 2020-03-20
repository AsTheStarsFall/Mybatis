package com.tianhy;

import com.google.gson.Gson;
import com.tianhy.domain.*;
import com.tianhy.mapper.TblempMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.junit.Test;
import org.nutz.json.Json;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * {@link}
 *
 * @Desc:
 * @Author: thy
 * @CreateTime: 2019/4/27
 **/
@Slf4j
public class MBGTest {
    public static Gson gson = new Gson();

    @Test
    public void testSelectByPrimaryKey() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            //映射
            TblempMapper tblempMapper = sqlSession.getMapper(TblempMapper.class);
            Tblemp tblemp = tblempMapper.selectByPrimaryKey(1);
            log.debug(gson.toJson(tblemp));
        } finally {
            sqlSession.close();
        }
    }


    //条件查询
    @Test
    public void selectByRoundBounds() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            //映射
            TblempMapper tblempMapper = sqlSession.getMapper(TblempMapper.class);

            TblempExample example = new TblempExample();
            TblempExample.Criteria criteria = example.createCriteria();
            criteria.andEmpIdIsNotNull();
            List<Tblemp> tblemps = tblempMapper.selectByExample(example);
            log.debug(Json.toJson(tblemps));
        } finally {
            sqlSession.close();
        }
    }


    @Test
    public void updateByPrimaryKey() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            //映射
            TblempMapper tblempMapper = sqlSession.getMapper(TblempMapper.class);
            Tblemp tblemp = new Tblemp();
            tblemp.setEmpId(1);
            tblemp.setEmail("thy@gmail.com");
            tblemp.setdId(1);
            tblemp.setGender("F");
            tblemp.setEmpName("王路");
            tblempMapper.updateByPrimaryKey(tblemp);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}
