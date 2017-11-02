package com.hellogood.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hellogood.constant.Code;
import com.hellogood.domain.Picture;
import com.hellogood.domain.PictureExample;
import com.hellogood.exception.BusinessException;
import com.hellogood.http.vo.PictureVO;
import com.hellogood.mapper.PictureMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * PictureService
 * Create by kejian
 */
@Service
@Transactional
public class PictureService {

    @Autowired
    private PictureMapper pictureMapper;

    private void checkCommon(PictureVO vo){
        if (StringUtils.isBlank(vo.getType()))
            throw new BusinessException("操作失败: 图片类型不能为空");
        if (StringUtils.isBlank(vo.getFileName()))
            throw new BusinessException("操作失败: 图片名称不能为空");
    }

    /**
     * 新增
     * @param vo
     */
    public void add(PictureVO vo) {
        checkCommon(vo);
        Picture domain = new Picture();
        vo.vo2Domain(domain);
        domain.setCreateTime(new Date());
        domain.setValidStatus(Code.STATUS_VALID);
        pictureMapper.insert(domain);
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(String ids) {
        if (StringUtils.isBlank(ids)) throw new BusinessException("请选择要删除的记录");
        String[] idStrArr = ids.split(",");
        for (String idStr : idStrArr) {
            Integer pictureId = Integer.parseInt(idStr);
            Picture picture = pictureMapper.selectByPrimaryKey(pictureId);
            if (picture == null) continue;
            picture.setValidStatus(Code.STATUS_INVALID);
            pictureMapper.updateByPrimaryKeySelective(picture);
        }
    }

    /**
     * 修改
     * @param vo
     */
    public void update(PictureVO vo) {
        checkCommon(vo);
        Picture domain = new Picture();
        vo.vo2Domain(domain);
        pictureMapper.updateByPrimaryKeySelective(domain);
    }
    /**
     * 获取数据
     * @param id
     * @return
     */
    public Picture getPicture(Integer id) {
        return pictureMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PictureVO get(Integer id) {
        PictureVO vo = new PictureVO();
        if (id == null)  return vo;
        Picture domain = getPicture(id);
        vo.domain2Vo(domain);
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
    public PageInfo pageQuery(PictureVO queryVo, int page, int pageSize) {
        PictureExample example = new PictureExample();
        PictureExample.Criteria criteria = example.createCriteria();
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
        example.setOrderByClause(" type, create_time desc");
        PageHelper.startPage(page, pageSize);
        List<Picture> list = pictureMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        List<PictureVO> voList = domainList2VoList(list);
        pageInfo.getList().clear();
        pageInfo.getList().addAll(voList);
        return pageInfo;
    }

    private List<PictureVO> domainList2VoList(List<Picture> domainList) {
        List<PictureVO> voList = new ArrayList<>(domainList.size());
        for (Picture domain : domainList) {
            PictureVO vo = new PictureVO();
            vo.domain2Vo(domain);
            voList.add(vo);
        }
        return voList;
    }
    
    /**
     * 保存图片并返回图片id
     * @param pictureId：图片id
     * @param imgName：图片名称
     * @param originalImgName：图片原始名称
     * @return
     */
    public Integer saveDatum(Integer pictureId, String imgName, String originalImgName) {
        if (pictureId == null) throw new BusinessException("参数有误");
        Picture picture = getPicture(pictureId);
        if (picture == null) throw new BusinessException("参数有误");
        picture.setFileName(imgName);
        picture.setOriginalFileName(originalImgName);
        pictureMapper.updateByPrimaryKey(picture);
        return picture.getId();
    }

}
