package com.hellogood.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hellogood.domain.Action;
import com.hellogood.domain.ActionExample;

public interface ActionMapper {
    int deleteByExample(ActionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Action record);

    int insertSelective(Action record);

    List<Action> selectByExample(ActionExample example);

    Action selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Action record, @Param("example") ActionExample example);

    int updateByExample(@Param("record") Action record, @Param("example") ActionExample example);

    int updateByPrimaryKeySelective(Action record);

    int updateByPrimaryKey(Action record);
}