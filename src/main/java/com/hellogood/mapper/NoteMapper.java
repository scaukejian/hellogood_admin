package com.hellogood.mapper;

import com.hellogood.domain.Note;
import com.hellogood.domain.NoteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoteMapper {
    int deleteByExample(NoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Note record);

    int insertSelective(Note record);

    List<Note> selectByExample(NoteExample example);

    Note selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Note record, @Param("example") NoteExample example);

    int updateByExample(@Param("record") Note record, @Param("example") NoteExample example);

    int updateByPrimaryKeySelective(Note record);

    int updateByPrimaryKey(Note record);
}