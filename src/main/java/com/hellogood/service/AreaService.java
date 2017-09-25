package com.hellogood.service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.hellogood.constant.EhCacheCode;
import com.hellogood.domain.Province;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hellogood.domain.Area;
import com.hellogood.domain.AreaExample;
import com.hellogood.mapper.AreaMapper;

/**
 * Created by kejian on 2017/9/30.
 */
@Service
@Transactional
public class AreaService {

	@Autowired
	private AreaMapper areaMapper;

	@Autowired
	private ProvinceService provinceService;


	@Cacheable(value = EhCacheCode.CACHE_TYPE_AREA, key = "#id")
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Area get(Integer id) {
		return areaMapper.selectByPrimaryKey(id);
	}

	/**
	 * 模糊查询获取城市信息
	 * 
	 * @param cityName
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Area> getByCityName(String cityName) {
		AreaExample example = new AreaExample();
		AreaExample.Criteria criteria = example.createCriteria();

		if (StringUtils.isNotBlank(cityName)) {// 模糊查询 城市名称 or 拼音
			example.or().andNameLike(MessageFormat.format("{0}%", cityName));
			example.or().andPinyinNameLike(
					MessageFormat.format("{0}%", cityName));
		}
		example.or(criteria);
		List<Area> areaList = areaMapper.selectByExample(example);
		return areaList;
	}

	/**
	 * 通过指定城市名获取城市信息
	 * 
	 * @param cityName
	 * @return
	 */
	@Cacheable(value = EhCacheCode.CACHE_TYPE_AREA, key = "#cityName")
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Area getEqualsCityName(String cityName) {
		AreaExample example = new AreaExample();
		AreaExample.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(cityName);
		List<Area> areas = areaMapper.selectByExample(example);
		if (areas != null && areas.size() > 0) {
			return areas.get(0);
		}
		return null;
	}

	public List<Area> getAreaByParentId(Integer parentId){
		AreaExample example = new AreaExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		return areaMapper.selectByExample(example);
	}

	public List<String> queryAreaNameByProvinceName(String provinceName){
		Province province = provinceService.getByName(provinceName);
		if(province == null) return null;
		AreaExample example = new AreaExample();
		example.createCriteria().andParentIdEqualTo(province.getCode());
		List<Area> list = areaMapper.selectByExample(example);
		if(list.isEmpty()) return null;
		return list.stream().map(area -> area.getName()).distinct().collect(Collectors.toList());
	}
}
