package com.hellogood.mapper;

import com.hellogood.domain.User;
import com.hellogood.domain.UserExample;
import java.util.List;

import com.hellogood.http.vo.UserVO;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    /**
     * 查找用户列表
     * @param userVO
     * @return
     */
    List<UserVO> listUserBySearch(UserVO userVO);

    /**
     * 统计当前查询条件下用户总数
     * @param userVO
     * @return
     */
    Integer getSearchTotal(UserVO userVO);
}