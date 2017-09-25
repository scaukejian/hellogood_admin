package com.hellogood.http.vo;

import com.hellogood.domain.PushMapping;
import com.hellogood.utils.BeaUtils;

import java.lang.reflect.InvocationTargetException;

public class PushMappingVO extends BaseVO<PushMapping> {
    private Integer id;

    private Integer userId;

    private String deviceName;

    private String clientId;

    private String deviceToken;

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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken == null ? null : deviceToken.trim();
    }

    public PushMappingVO() {
    }

    public PushMappingVO(Integer userId, String deviceName, String clientId, String deviceToken) {
        this.userId = userId;
        this.deviceName = deviceName;
        this.clientId = clientId;
        this.deviceToken = deviceToken;
    }

    @Override
	public void domain2Vo(PushMapping t) {
		try {
			BeaUtils.copyProperties(this, t);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void vo2Domain(PushMapping t) {
		try {
			BeaUtils.copyProperties(t, this);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

    @Override
    public String toString() {
        return "PushMappingVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", deviceName='" + deviceName + '\'' +
                ", clientId='" + clientId + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                '}';
    }
}