package com.tianhy.mapper;

import com.tianhy.domain.Tblemp;
import com.tianhy.domain.TblempExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TblempMapper {
    long countByExample(TblempExample example);

    int deleteByExample(TblempExample example);

    int deleteByPrimaryKey(Integer empId);

    int insert(Tblemp record);

    int insertSelective(Tblemp record);

    List<Tblemp> selectByExample(TblempExample example);

    Tblemp selectByPrimaryKey(Integer empId);

    int updateByExampleSelective(@Param("record") Tblemp record, @Param("example") TblempExample example);

    int updateByExample(@Param("record") Tblemp record, @Param("example") TblempExample example);

    int updateByPrimaryKeySelective(Tblemp record);

    int updateByPrimaryKey(Tblemp record);
}