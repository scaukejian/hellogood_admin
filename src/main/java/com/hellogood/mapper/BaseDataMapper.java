package com.hellogood.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hellogood.domain.BaseData;
import com.hellogood.domain.BaseDataExample;

public interface BaseDataMapper {
    int deleteByExample(BaseDataExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BaseData record);

    int insertSelective(BaseData record);

    List<BaseData> selectByExample(BaseDataExample example);

    BaseData selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BaseData record, @Param("example") BaseDataExample example);

    int updateByExample(@Param("record") BaseData record, @Param("example") BaseDataExample example);

    int updateByPrimaryKeySelective(BaseData record);

    int updateByPrimaryKey(BaseData record);
}