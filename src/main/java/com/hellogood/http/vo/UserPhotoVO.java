package com.hellogood.http.vo;

import com.hellogood.domain.UserPhoto;
import com.hellogood.exception.BusinessException;
import com.hellogood.utils.BeaUtils;

import java.util.Date;

public class UserPhotoVO {
    private Integer id;

    private Integer userId;

    private String imgName;

    private String originalImgName;

    private Integer headFlag;

    private Date updateTime;

    private String thumbnailImgName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName == null ? null : imgName.trim();
    }

    public String getOriginalImgName() {
        return originalImgName;
    }

    public void setOriginalImgName(String originalImgName) {
        this.originalImgName = originalImgName == null ? null : originalImgName.trim();
    }

    public Integer getHeadFlag() {
        return headFlag;
    }

    public void setHeadFlag(Integer headFlag) {
        this.headFlag = headFlag;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getThumbnailImgName() {
        return thumbnailImgName;
    }

    public void setThumbnailImgName(String thumbnailImgName) {
        this.thumbnailImgName = thumbnailImgName == null ? null : thumbnailImgName.trim();
    }
    public void vo2Domain(UserPhoto domain) {
        try {
            BeaUtils.copyProperties(domain, this);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取用户图片信息失败");
        }
    }

    public void domain2Vo(UserPhoto domain) {
        try {
            BeaUtils.copyProperties(this, domain);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取用户图片信息失败");
        }
    }
}