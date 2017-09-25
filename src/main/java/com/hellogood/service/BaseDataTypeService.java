package com.hellogood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hellogood.domain.BaseDataType;
import com.hellogood.domain.BaseDataTypeExample;
import com.hellogood.http.vo.BaseDataTypeVO;
import com.hellogood.mapper.BaseDataTypeMapper;

/**
 * 基础数据类型服务 Created by kejian
 */
@Service
@Transactional
public class BaseDataTypeService {
	
	@Autowired
	private BaseDataTypeMapper baseDataTypeMapper;

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<BaseDataTypeVO> getAll() {
		List<BaseDataTypeVO> list = new ArrayList<BaseDataTypeVO>();
		List<BaseDataType> baseDataTypes = baseDataTypeMapper
				.selectByExample(new BaseDataTypeExample());
		for (BaseDataType baseDataType : baseDataTypes) {
			BaseDataTypeVO baseDataTypeVO = new BaseDataTypeVO();
			baseDataTypeVO.domain2Vo(baseDataType);
			list.add(baseDataTypeVO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public BaseDataTypeVO get(Long id) {
		BaseDataType baseDataType = baseDataTypeMapper.selectByPrimaryKey(id);
		BaseDataTypeVO baseDataTypeVO = new BaseDataTypeVO();
		baseDataTypeVO.domain2Vo(baseDataType);
		return baseDataTypeVO;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public BaseDataTypeVO getByCode(String code) {
		BaseDataTypeVO vo = new BaseDataTypeVO();
		BaseDataTypeExample example = new BaseDataTypeExample();
		example.createCriteria().andCodeEqualTo(code);
		List<BaseDataType> list = baseDataTypeMapper.selectByExample(example);
		if (!list.isEmpty()) {
			BaseDataType type = list.get(0);
			vo.domain2Vo(type);
		}
		return vo;
	}
}
