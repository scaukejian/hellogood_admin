package com.hellogood.http.vo;

import com.hellogood.domain.Picture;
import com.hellogood.exception.BusinessException;
import com.hellogood.utils.BeaUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PictureVO {
    private Integer id;

    private String type;

    private String fileName;

    private String originalFileName;

    private Date createTime;

    private Integer validStatus;

    // 开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    // 截止日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName == null ? null : originalFileName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }


    public void vo2Domain(Picture domain) {
        try {
            BeaUtils.copyProperties(domain, this);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取图片资源失败");
        }
    }

    public void domain2Vo(Picture domain) {
        try {
            BeaUtils.copyProperties(this, domain);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取图片资源失败");
        }
    }

    @Override
    public String toString() {
        return "PictureVO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", fileName='" + fileName + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", createTime=" + createTime +
                ", validStatus=" + validStatus +
                ", startDate=" + startDate +
                ", deadline=" + deadline +
                '}';
    }
}
