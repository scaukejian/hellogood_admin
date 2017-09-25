package com.hellogood.http.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hellogood.constant.Code;
import com.hellogood.enumeration.BaseDataType;
import com.hellogood.http.vo.BaseDataTypeVO;
import com.hellogood.http.vo.BaseDataVO;
import com.hellogood.service.BaseDataService;
import com.hellogood.service.BaseDataTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 基础数据控制器 Created by kejian
 */
@Controller
@RequestMapping("baseData")
public class BaseDataController extends BaseController {
	
	@Autowired
	public BaseDataService baseDataService;
	@Autowired
	public BaseDataTypeService baseDataTypeService;

	@ResponseBody
	@RequestMapping(value = "/add.do", method = RequestMethod.POST)
	public Map<String, Object> add(@ModelAttribute BaseDataVO baseDataVO,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		baseDataService.add(baseDataVO);
		// 刷新缓存
		reflashehcache(request, Code.REFLASH_EHCACHE_BASE);
		result.put(STATUS, STATUS_SUCCESS);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public Map<String, Object> update(@ModelAttribute BaseDataVO baseDataVO,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(baseDataVO.getType())) {
			BaseDataTypeVO baseDataTypeVO = baseDataTypeService
					.getByCode(baseDataVO.getType());
			baseDataVO.setType(baseDataTypeVO.getCode());
		}
		baseDataService.update(baseDataVO);
		// 刷新缓存
		reflashehcache(request, Code.REFLASH_EHCACHE_BASE);
		result.put(STATUS, STATUS_SUCCESS);
		return result;
	}

	@ResponseBody
	@RequestMapping("/get/{id}.do")
	public Map<String, Object> get(@PathVariable Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(DATA, baseDataService.get(id));
		result.put(DATA_LIST, baseDataTypeService.getAll());
		result.put(STATUS, STATUS_SUCCESS);
		return result;
	}

	@ResponseBody
	@RequestMapping("/pageJson.do")
	public Map<String, Object> pageJson(BaseDataVO baseDataVO,
			@RequestParam int page, @RequestParam int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageInfo pageInfo = baseDataService
				.pageJson(baseDataVO, page, pageSize);
		map.put(DATA_LIST, pageInfo.getList());
		map.put(TOTAL, pageInfo.getTotal());
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/modifyStatus/{id}-{status}.do")
	public Map<String, Object> modifyStatus(@PathVariable Integer id,
			@PathVariable Integer status, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = baseDataService.modifyStatusById(id, status);
		if (result) {
			map.put(STATUS, STATUS_SUCCESS);
			// 刷新缓存
			reflashehcache(request, Code.REFLASH_EHCACHE_BASE);
		} else {
			map.put(ERROR_MSG, "网络异常！请稍后再试!");
		}
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping("/getRegisterBaseData.do")
	public Map<String, Object> getRegisterBaseData(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("degreeList", baseDataService.getAllByType(BaseDataType.DEGREE.getCode(),Code.STATUS_VALID));
		map.put("assetList", baseDataService.getAllByType(BaseDataType.ASSET.getCode(),Code.STATUS_VALID));
		map.put("marryList", baseDataService.getAllByType(BaseDataType.MARRY.getCode(),Code.STATUS_VALID));
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}
}
