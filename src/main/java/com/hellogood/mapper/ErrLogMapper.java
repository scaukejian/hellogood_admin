package com.hellogood.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hellogood.domain.ErrLog;
import com.hellogood.domain.ErrLogExample;

public interface ErrLogMapper {
    int deleteByExample(ErrLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ErrLog record);

    int insertSelective(ErrLog record);

    List<ErrLog> selectByExampleWithBLOBs(ErrLogExample example);

    List<ErrLog> selectByExample(ErrLogExample example);

    ErrLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ErrLog record, @Param("example") ErrLogExample example);

    int updateByExampleWithBLOBs(@Param("record") ErrLog record, @Param("example") ErrLogExample example);

    int updateByExample(@Param("record") ErrLog record, @Param("example") ErrLogExample example);

    int updateByPrimaryKeySelective(ErrLog record);

    int updateByPrimaryKeyWithBLOBs(ErrLog record);

    int updateByPrimaryKey(ErrLog record);
}