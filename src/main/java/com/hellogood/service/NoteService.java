package com.hellogood.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hellogood.constant.Code;
import com.hellogood.domain.*;
import com.hellogood.exception.BusinessException;
import com.hellogood.http.vo.NoteVO;
import com.hellogood.mapper.NoteMapper;
import com.hellogood.mapper.UserMapper;
import com.hellogood.utils.DataUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * NoteService
 * Create by kejian
 */
@Service
@Transactional
public class NoteService {

    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FolderService folderService;

    private void checkCommon(NoteVO vo){
        if (vo.getUserId() == null && StringUtils.isBlank(vo.getPhoneUniqueCode()))
            throw new BusinessException("请输入正确的账号或者手机唯一标识");
        if (StringUtils.isBlank(vo.getContent()))
            throw new BusinessException("操作失败: 计划内容不能为空");
        vo.setContent(DataUtil.strToconent(vo.getContent())); //过滤编辑器的特殊格式
        if (vo.getContent().length() > 5000)
            throw new BusinessException("操作失败: 内容长度不能大于5000个字符");
        if (vo.getFolderId() == null || vo.getFolderId() == 0)
            throw new BusinessException("操作失败: 请选择所属文件夹");
        Folder folder = folderService.getFolder(vo.getFolderId());
        if (folder == null) throw new BusinessException("操作失败: 文件夹id有误");
    }

    /**
     * 新增
     * @param vo
     */
    public void add(NoteVO vo) {
        checkCommon(vo);
        if (StringUtils.isBlank(vo.getColor())) vo.setColor("#FFF");
        Note domain = new Note();
        vo.vo2Domain(domain);
        domain.setCreateTime(new Date());
        domain.setUpdateTime(new Date());
        domain.setValidStatus(Code.STATUS_VALID);
        if (vo.getDisplay() == null) domain.setDisplay(Code.STATUS_VALID);
        if (vo.getTop() == null) domain.setTop(Code.STATUS_INVALID);
        if (vo.getFinish() == null) domain.setFinish(Code.STATUS_INVALID);
        noteMapper.insert(domain);
    }

    /**
     * 设置状态
     * @param ids
     */
    public void setStatus(String ids, Integer status) {
        if (StringUtils.isBlank(ids)) throw new BusinessException("请选择要操作的记录");
        if (status == null) throw new BusinessException("参数有误");
        String[] idStrArr = ids.split(",");
        for (String idStr : idStrArr) {
            Integer noteId = Integer.parseInt(idStr);
            Note note = noteMapper.selectByPrimaryKey(noteId);
            if (note == null) continue;
            if (status == note.getValidStatus()) continue;
            note.setValidStatus(status);
            noteMapper.updateByPrimaryKeySelective(note);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(String ids) {
        if (StringUtils.isBlank(ids)) throw new BusinessException("请选择要删除的记录");
        String[] idStrArr = ids.split(",");
        for (String idStr : idStrArr) {
            Integer noteId = Integer.parseInt(idStr);
            Note note = noteMapper.selectByPrimaryKey(noteId);
            if (note == null) continue;
            note.setValidStatus(Code.STATUS_INVALID);
            noteMapper.updateByPrimaryKeySelective(note);
        }
    }

    /**
     * 修改
     * @param vo
     */
    public void update(NoteVO vo) {
        checkCommon(vo);
        Note domain = new Note();
        vo.vo2Domain(domain);
        domain.setUpdateTime(new Date());
        noteMapper.updateByPrimaryKeySelective(domain);
    }
    /**
     * 获取数据
     * @param id
     * @return
     */
    public Note getNote(Integer id) {
        return noteMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public NoteVO get(Integer id) {
        NoteVO vo = new NoteVO();
        if (id == null)  return vo;
        Note domain = getNote(id);
        vo.domain2Vo(domain);
        supplement(vo);
        return vo;
    }

    /**
     * 分页查询
     * @param queryVo
     * @param page
     * @param pageSize
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PageInfo pageQuery(NoteVO queryVo, int page, int pageSize) {
        NoteExample example = new NoteExample();
        NoteExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(queryVo.getUserName()) || StringUtils.isNotBlank(queryVo.getPhone()))
            criteria.andUserIdIn(getUserIds(queryVo));
        if (StringUtils.isNotBlank(queryVo.getPhoneUniqueCode()))
            criteria.andPhoneUniqueCodeLike(MessageFormat.format("%{0}%", queryVo.getPhoneUniqueCode()));
        if (StringUtils.isNotBlank(queryVo.getContent()))
            criteria.andContentLike(MessageFormat.format("%{0}%", queryVo.getContent()));
        if (queryVo.getFolderId() != null)
            criteria.andFolderIdEqualTo(queryVo.getFolderId());
        if (queryVo.getTop() != null)
            criteria.andTopEqualTo(queryVo.getTop());
        if (queryVo.getFinish() != null)
            criteria.andFinishEqualTo(queryVo.getFinish());
        if (queryVo.getDisplay() != null)
            criteria.andDisplayEqualTo(queryVo.getDisplay());
        if (queryVo.getStartDate() != null) // 开始日期
            criteria.andCreateTimeGreaterThanOrEqualTo(queryVo.getStartDate());
        // 截止日期
        if (queryVo.getDeadline() != null)  {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(queryVo.getDeadline());
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            criteria.andCreateTimeLessThanOrEqualTo(calendar.getTime());
        }

        criteria.andValidStatusEqualTo(Code.STATUS_VALID);
        example.setOrderByClause(" top desc, update_time desc");
        PageHelper.startPage(page, pageSize);
        List<Note> list = noteMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        List<NoteVO> voList = domainList2VoList(list);
        pageInfo.getList().clear();
        pageInfo.getList().addAll(voList);
        return pageInfo;
    }

    private List<NoteVO> domainList2VoList(List<Note> domainList) {
        List<NoteVO> voList = new ArrayList<>(domainList.size());
        for (Note domain : domainList) {
            NoteVO vo = new NoteVO();
            vo.domain2Vo(domain);
            supplement(vo);
            voList.add(vo);
        }
        return voList;
    }

    public void supplement(NoteVO vo) {
        if (vo.getUserId() != null) {
            User user = userMapper.selectByPrimaryKey(vo.getUserId());
            if (user != null) {
                vo.setUserCode(user.getUserCode());
                vo.setUserName(user.getUserName());
                vo.setPhone(user.getPhone());
            }
        }
        if (vo.getFolderId() != null) {
            Folder folder = folderService.getFolder(vo.getFolderId());
            vo.setFolderName(folder.getName());
        }
    }


    /**
     * 查找计划id集合
     * @param queryVo
     * @return
     */
    public List<Integer> getUserIds(NoteVO queryVo) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(queryVo.getUserName()))
            criteria.andUserNameLike(MessageFormat.format("%{0}%", queryVo.getUserName()));
        if (StringUtils.isNotBlank(queryVo.getPhone()))
            criteria.andPhoneLike(MessageFormat.format("%{0}%", queryVo.getPhone()));
        List<User> userList = userMapper.selectByExample(example);
        List<Integer> userIdList = new ArrayList<>();
        if (!userList.isEmpty()) userIdList = userList.stream().map(user -> user.getId()).collect(Collectors.toList());
        if (userIdList.isEmpty()) userIdList.add(-1);
        return userIdList;
    }

}
