package com.hellogood.http.vo;

import com.hellogood.domain.BaseDataType;
import com.hellogood.exception.BusinessException;
import com.hellogood.utils.BeaUtils;

/**
 * Created by kejian
 */
public class BaseDataTypeVO {
	private Long id;

    private String code;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public void vo2Domain(BaseDataType domain){
        try {
            BeaUtils.copyProperties(domain, this);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("获取基础数据类型失败");
        }
    }

    public void domain2Vo(BaseDataType domain){
        try {
            BeaUtils.copyProperties(this, domain);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("获取基础数据类型失败");
        }
    }
}
