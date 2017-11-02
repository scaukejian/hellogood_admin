package com.hellogood.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hellogood.constant.Code;
import com.hellogood.constant.EhCacheCode;
import com.hellogood.domain.Folder;
import com.hellogood.domain.FolderExample;
import com.hellogood.domain.User;
import com.hellogood.domain.UserExample;
import com.hellogood.exception.BusinessException;
import com.hellogood.http.vo.FolderVO;
import com.hellogood.mapper.FolderMapper;
import com.hellogood.mapper.UserMapper;
import com.hellogood.utils.DataUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FolderService
 * Create by kejian
 */
@Service
@Transactional
public class FolderService {

    @Autowired
    private FolderMapper folderMapper;
    @Autowired
    private UserMapper userMapper;

    private void checkCommon(FolderVO vo){
        if (StringUtils.isBlank(vo.getName()))
            throw new BusinessException("操作失败: 文件夹名称不能为空");
        if (vo.getName().length() > 20)
            throw new BusinessException("操作失败: 文件夹名称不能大于20个字符");
        if (vo.getId() == null) {
            if (vo.getSystemFolder() == null)
                throw new BusinessException("操作失败: 请选择是否系统文件夹");
            if (vo.getSystemFolder() == Code.STATUS_INVALID && vo.getUserId() == null)
                throw new BusinessException("操作失败：非系统文件夹必须输入该文件夹所属用户姓名");
        }
    }

    /**
     * 新增
     * @param vo
     */
    public void add(FolderVO vo) {
        checkCommon(vo);
        Folder domain = new Folder();
        vo.vo2Domain(domain);
        domain.setCreateTime(new Date());
        domain.setUpdateTime(new Date());
        domain.setValidStatus(Code.STATUS_VALID);
        folderMapper.insert(domain);
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(String ids) {
        if (StringUtils.isBlank(ids)) throw new BusinessException("请选择要删除的记录");
        String[] idStrArr = ids.split(",");
        for (String idStr : idStrArr) {
            Integer folderId = Integer.parseInt(idStr);
            Folder folder = folderMapper.selectByPrimaryKey(folderId);
            if (folder == null) continue;
            folder.setValidStatus(Code.STATUS_INVALID);
            folderMapper.updateByPrimaryKeySelective(folder);
        }
    }

    /**
     * 修改
     * @param vo
     */
    public void update(FolderVO vo) {
        checkCommon(vo);
        Folder domain = new Folder();
        vo.vo2Domain(domain);
        domain.setUpdateTime(new Date());
        folderMapper.updateByPrimaryKeySelective(domain);
    }
    /**
     * 获取数据
     * @param id
     * @return
     */
    public Folder getFolder(Integer id) {
        return folderMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public FolderVO get(Integer id) {
        FolderVO vo = new FolderVO();
        if (id == null)  return vo;
        Folder domain = getFolder(id);
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
    public PageInfo pageQuery(FolderVO queryVo, int page, int pageSize) {
        FolderExample example = new FolderExample();
        FolderExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(queryVo.getUserName()) || StringUtils.isNotBlank(queryVo.getPhone()))
            criteria.andUserIdIn(getUserIds(queryVo));
        if (StringUtils.isNotBlank(queryVo.getName()))
            criteria.andNameLike(MessageFormat.format("%{0}%", queryVo.getName()));
        if (queryVo.getSystemFolder() != null)
            criteria.andSystemFolderEqualTo(queryVo.getSystemFolder());
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
        example.setOrderByClause(" system_folder desc, update_time desc");
        PageHelper.startPage(page, pageSize);
        List<Folder> list = folderMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        List<FolderVO> voList = domainList2VoList(list);
        pageInfo.getList().clear();
        pageInfo.getList().addAll(voList);
        return pageInfo;
    }

    private List<FolderVO> domainList2VoList(List<Folder> domainList) {
        List<FolderVO> voList = new ArrayList<>(domainList.size());
        for (Folder domain : domainList) {
            FolderVO vo = new FolderVO();
            vo.domain2Vo(domain);
            supplement(vo);
            voList.add(vo);
        }
        return voList;
    }

    public void supplement(FolderVO vo) {
        if (vo.getUserId() != null) {
            User user = userMapper.selectByPrimaryKey(vo.getUserId());
            if (user != null) {
                vo.setUserCode(user.getUserCode());
                vo.setUserName(user.getUserName());
                vo.setPhone(user.getPhone());
            }
        }
    }


    /**
     * 查找文件夹id集合
     * @param queryVo
     * @return
     */
    public List<Integer> getUserIds(FolderVO queryVo) {
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
