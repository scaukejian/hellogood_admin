package com.hellogood.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hellogood.domain.BaseDataType;
import com.hellogood.domain.BaseDataTypeExample;

public interface BaseDataTypeMapper {
    int deleteByExample(BaseDataTypeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BaseDataType record);

    int insertSelective(BaseDataType record);

    List<BaseDataType> selectByExample(BaseDataTypeExample example);

    BaseDataType selectByPrimaryKey(Long id);
    BaseDataType selectByType(String  type);
    int updateByExampleSelective(@Param("record") BaseDataType record, @Param("example") BaseDataTypeExample example);

    int updateByExample(@Param("record") BaseDataType record, @Param("example") BaseDataTypeExample example);

    int updateByPrimaryKeySelective(BaseDataType record);

    int updateByPrimaryKey(BaseDataType record);
}