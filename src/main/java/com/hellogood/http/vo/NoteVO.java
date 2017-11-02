package com.hellogood.http.vo;

import com.hellogood.domain.Note;
import com.hellogood.exception.BusinessException;
import com.hellogood.utils.BeaUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class NoteVO {
    private Integer id;

    private Integer userId;

    private String phoneUniqueCode;

    private String content;

    private Date createTime;

    private Date updateTime;

    private Integer validStatus;

    private Integer display;

    private Integer top;

    private String type;

    private String color;

    private String userCode;

    private String userName;

    private String phone;

    // 开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    // 截止日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;


    private Integer finish;

    public Integer getFinish() {
        return finish;
    }

    public void setFinish(Integer finish) {
        this.finish = finish;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getPhoneUniqueCode() {
        return phoneUniqueCode;
    }

    public void setPhoneUniqueCode(String phoneUniqueCode) {
        this.phoneUniqueCode = phoneUniqueCode == null ? null : phoneUniqueCode.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }


    public void vo2Domain(Note domain) {
        try {
            BeaUtils.copyProperties(domain, this);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取计划信息失败");
        }
    }

    public void domain2Vo(Note domain) {
        try {
            BeaUtils.copyProperties(this, domain);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取计划信息失败");
        }
    }

    @Override
    public String toString() {
        return "NoteVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", phoneUniqueCode='" + phoneUniqueCode + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", validStatus=" + validStatus +
                ", display=" + display +
                ", top=" + top +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", startDate=" + startDate +
                ", deadline=" + deadline +
                ", finish=" + finish +
                '}';
    }
}