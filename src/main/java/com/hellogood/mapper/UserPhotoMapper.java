package com.hellogood.mapper;

import com.hellogood.domain.UserPhoto;
import com.hellogood.domain.UserPhotoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPhotoMapper {
    int deleteByExample(UserPhotoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserPhoto record);

    int insertSelective(UserPhoto record);

    List<UserPhoto> selectByExample(UserPhotoExample example);

    UserPhoto selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserPhoto record, @Param("example") UserPhotoExample example);

    int updateByExample(@Param("record") UserPhoto record, @Param("example") UserPhotoExample example);

    int updateByPrimaryKeySelective(UserPhoto record);

    int updateByPrimaryKey(UserPhoto record);
}