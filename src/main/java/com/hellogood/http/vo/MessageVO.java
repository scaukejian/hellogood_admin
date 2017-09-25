package com.hellogood.http.vo;

import com.hellogood.domain.Message;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kejian on 2017/9/16.
 */
public class MessageVO implements Serializable{
    private Integer id;

    private Integer userId;

    private String content;

    private Date createTime;

    private Integer type;

    private String typeName;

    private Integer status;

    private String statusName;
    
    private String senderHeadPhotoName;
    
    private String senderSex;
    
    private String senderFirstName;
    
    private Integer senderInfoId;

    //消息推送
    private String senderName;

    private String phone;

    private String clientType;

    private String deviceToken;

    private String clientId;

    private String pushType;//LIST, SINGLE

    private List<String> clientIdList;

    private List<String> deviceTokenList;

    private Integer clientProVersionFlag;//易悦精英客户端标识 1-是, 0-否

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	

	public String getSenderHeadPhotoName() {
		return senderHeadPhotoName;
	}

	public void setSenderHeadPhotoName(String senderHeadPhotoName) {
		this.senderHeadPhotoName = senderHeadPhotoName;
	}

	public String getSenderSex() {
		return senderSex;
	}

	public void setSenderSex(String senderSex) {
		this.senderSex = senderSex;
	}

	public String getSenderFirstName() {
		return senderFirstName;
	}

	public void setSenderFirstName(String senderFirstName) {
		this.senderFirstName = senderFirstName;
	}

	public Integer getSenderInfoId() {
		return senderInfoId;
	}

	public void setSenderInfoId(Integer senderInfoId) {
		this.senderInfoId = senderInfoId;
	}

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<String> getClientIdList() {
        return clientIdList;
    }

    public void setClientIdList(List<String> clientIdList) {
        this.clientIdList = clientIdList;
    }

    public List<String> getDeviceTokenList() {
        return deviceTokenList;
    }

    public void setDeviceTokenList(List<String> deviceTokenList) {
        this.deviceTokenList = deviceTokenList;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public Integer getClientProVersionFlag() {
        return clientProVersionFlag;
    }

    public void setClientProVersionFlag(Integer clientProVersionFlag) {
        this.clientProVersionFlag = clientProVersionFlag;
    }

    @Override
    public String toString() {
        return "MessageVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", type=" + type +
                ", typeName='" + typeName + '\'' +
                ", status=" + status +
                ", statusName='" + statusName + '\'' +
                ", senderHeadPhotoName='" + senderHeadPhotoName + '\'' +
                ", senderSex='" + senderSex + '\'' +
                ", senderFirstName='" + senderFirstName + '\'' +
                ", senderInfoId=" + senderInfoId +
                ", senderName='" + senderName + '\'' +
                ", phone='" + phone + '\'' +
                ", clientType='" + clientType + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                ", clientId='" + clientId + '\'' +
                ", pushType='" + pushType + '\'' +
                ", clientIdList=" + clientIdList +
                ", deviceTokenList=" + deviceTokenList +
                ", clientProVersionFlag=" + clientProVersionFlag +
                '}';
    }

    public void domain2Vo(Message domain){
        if(domain == null){
            return;
        }
        try {
            BeanUtils.copyProperties(this, domain);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void vo2Domain(Message domain){
        if(domain == null){
            return;
        }
        try {
            BeanUtils.copyProperties(domain, this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
