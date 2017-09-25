package com.hellogood.http.vo;


import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.hellogood.domain.BaseData;
import com.hellogood.exception.BusinessException;
import com.hellogood.utils.BeaUtils;

/**
 *Created by kejian on 2017/9/21.
 */
public class BaseDataVO {

	private Integer id;

	private String type;

	private String code;

	private String name;
	private Integer status;

	private Date createTime;

	private Date updateTime;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BaseDataVO{" +
				"id=" + id +
				", type='" + type + '\'' +
				", code='" + code + '\'' +
				", name='" + name + '\'' +
				", status=" + status +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}

	public void vo2Domain(BaseData domain) {
		try {
			BeaUtils.copyProperties(domain, this);
			if(StringUtils.equals(type, "sign")){
				domain.setName(setNameStrContent(this.name));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("获取基础数据失败");
		}
	}

	private String setNameStrContent(String name) {
		name=StringUtils.replace(name,"&lt;", "<");
		name=StringUtils.replace(name,"&gt;", ">");
		name=StringUtils.replace(name,"&quot;", "\"");
		name=StringUtils.replace(name,"&amp;", "&");
		name=StringUtils.replace(name,"&#39;", "'");
		return name;
	}

	public void domain2Vo(BaseData domain) {
		try {
			BeaUtils.copyProperties(this, domain);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("获取基础数据失败");
		}
	}
}
