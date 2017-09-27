package com.hellogood.http.vo;

import com.hellogood.domain.User;
import com.hellogood.exception.BusinessException;
import com.hellogood.utils.BeaUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class UserVO {
    private Integer id;

    private String userCode;

    private String userName;

    private String sex;

    private Integer age;

    private Integer height;

    private Integer weight;

    private String phone;

    private String weixinName;

    private String degree;

    private Date birthday;

    private String constellation;

    private String maritalStatus;

    private String nativePlace;

    private String liveProvince;

    private String liveCity;

    private String nationality;

    private String school;

    private String company;

    private String job;

    private String characteristicSignature;

    private Date createTime;

    private Date updateTime;

    private Integer validStatus;

    private Integer page;

    private Integer pageSize;

    private Integer minAge;

    private Integer maxAge;

    private Date birthDayBeginTime;

    private Date birthDayEndTime;

    private Integer minHeight;

    private Integer maxHeight;

    // 开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    // 截止日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    private List<String> cityList;

    private String remark;

    private String qq;

    private String email;

    private UserPhotoVO headPhoto;//头像

    private String headPhotoName;

    private String headPicPath;

    private Integer headPhotoId;

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getHeadPhotoId() {
        return headPhotoId;
    }

    public void setHeadPhotoId(Integer headPhotoId) {
        this.headPhotoId = headPhotoId;
    }

    public UserPhotoVO getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(UserPhotoVO headPhoto) {
        this.headPhoto = headPhoto;
    }

    public String getHeadPicPath() {
        return headPicPath;
    }

    public void setHeadPicPath(String headPicPath) {
        this.headPicPath = headPicPath;
    }

    public String getHeadPhotoName() {
        return headPhotoName;
    }

    public void setHeadPhotoName(String headPhotoName) {
        this.headPhotoName = headPhotoName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
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

    public Integer getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(Integer minHeight) {
        this.minHeight = minHeight;
    }

    public Integer getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Integer maxHeight) {
        this.maxHeight = maxHeight;
    }

    public Date getBirthDayBeginTime() {
        return birthDayBeginTime;
    }

    public void setBirthDayBeginTime(Date birthDayBeginTime) {
        this.birthDayBeginTime = birthDayBeginTime;
    }

    public Date getBirthDayEndTime() {
        return birthDayEndTime;
    }

    public void setBirthDayEndTime(Date birthDayEndTime) {
        this.birthDayEndTime = birthDayEndTime;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getWeixinName() {
        return weixinName;
    }

    public void setWeixinName(String weixinName) {
        this.weixinName = weixinName == null ? null : weixinName.trim();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation == null ? null : constellation.trim();
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus == null ? null : maritalStatus.trim();
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public String getLiveProvince() {
        return liveProvince;
    }

    public void setLiveProvince(String liveProvince) {
        this.liveProvince = liveProvince == null ? null : liveProvince.trim();
    }

    public String getLiveCity() {
        return liveCity;
    }

    public void setLiveCity(String liveCity) {
        this.liveCity = liveCity == null ? null : liveCity.trim();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getCharacteristicSignature() {
        return characteristicSignature;
    }

    public void setCharacteristicSignature(String characteristicSignature) {
        this.characteristicSignature = characteristicSignature == null ? null : characteristicSignature.trim();
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

    public void vo2Domain(User domain) {
        try {
            BeaUtils.copyProperties(domain, this);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取用户信息失败");
        }
    }

    public void domain2Vo(User domain) {
        try {
            BeaUtils.copyProperties(this, domain);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取用户信息失败");
        }
    }
}