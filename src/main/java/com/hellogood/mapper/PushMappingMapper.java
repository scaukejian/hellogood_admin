package com.hellogood.mapper;

import com.hellogood.domain.PushMapping;
import com.hellogood.domain.PushMappingExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PushMappingMapper {
    int deleteByExample(PushMappingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PushMapping record);

    int insertSelective(PushMapping record);

    List<PushMapping> selectByExample(PushMappingExample example);

    PushMapping selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PushMapping record, @Param("example") PushMappingExample example);

    int updateByExample(@Param("record") PushMapping record, @Param("example") PushMappingExample example);

    int updateByPrimaryKeySelective(PushMapping record);

    int updateByPrimaryKey(PushMapping record);

}