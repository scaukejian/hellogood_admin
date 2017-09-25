package com.hellogood.service;

import com.hellogood.domain.Province;
import com.hellogood.domain.ProvinceExample;
import com.hellogood.exception.BusinessException;
import com.hellogood.mapper.ProvinceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProvinceService {

	@Autowired
	private ProvinceMapper provinceMapper;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Province getByCode(Integer code){
		if(code==null){
			throw new BusinessException("省编号不能为空");
		}
		ProvinceExample example=new ProvinceExample();
		example.createCriteria().andCodeEqualTo(code);
		List<Province> provinces=provinceMapper.selectByExample(example);
		if(provinces!=null && !provinces.isEmpty()){
			return provinces.get(0);
		}
		return null;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Province getByName(String name){
		ProvinceExample example=new ProvinceExample();
		example.createCriteria().andNameEqualTo(name);
		List<Province> provinces=provinceMapper.selectByExample(example);
		if(provinces!=null && !provinces.isEmpty()){
			return provinces.get(0);
		}
		return null;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Province> getAll(){
		return provinceMapper.selectByExample(new ProvinceExample());
	}
}
