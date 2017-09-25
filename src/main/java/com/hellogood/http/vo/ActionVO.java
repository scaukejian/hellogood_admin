package com.hellogood.http.vo;

import com.hellogood.domain.Action;
import com.hellogood.exception.BusinessException;
import com.hellogood.utils.BeaUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ActionVO implements Serializable {

	private static final long serialVersionUID = -5783021227419622489L;

	private Long id;

	private String name;

	private String url;

	private String type;

	private Byte available;

	private String description;

	private Long parent;
	private Long seqnum;

	private Date createtime;

	private Date updatetime;
	private List<String> role;//角色名
	
	//菜单类型
	private String typeName;
	
	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Long getSeqnum() {
		return seqnum;
	}

	public void setSeqnum(Long seqnum) {
		this.seqnum = seqnum;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Byte getAvailable() {
		return available;
	}

	public void setAvailable(Byte available) {
		this.available = available;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void vo2Domain(Action action) {
		try {
			BeaUtils.copyProperties(action, this);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("获取菜单信息失败！");
		}
	}

	public void domain2VO(Action action) {
		try {
			BeaUtils.copyProperties(this, action);
			this.typeName=setTypeName(action.getType());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("获取菜单信息失败");
		}
	}
	
	private String setTypeName(String type){
		if(StringUtils.equals(type, "1")){
			return "一级菜单";
		}else if(StringUtils.equals(type, "2")){
			return "二级菜单";
		}else if(StringUtils.equals(type, "2")){
			return "三级菜单";
		}else{
			return "";
		}
	}
	public String getTypeName() {
		return typeName;
	}

	

}
