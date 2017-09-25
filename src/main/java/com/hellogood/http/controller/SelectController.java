package com.hellogood.http.controller;

import com.hellogood.constant.Code;
import com.hellogood.domain.Area;
import com.hellogood.enumeration.BaseDataType;
import com.hellogood.http.vo.AreaVO;
import com.hellogood.http.vo.BaseDataTypeVO;
import com.hellogood.http.vo.BaseDataVO;
import com.hellogood.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 下拉框初始化 Created by kejian
 */
@RequestMapping("/select")
@Controller
public class SelectController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(SelectController.class);

	@Autowired
	private BaseDataTypeService baseDataTypeService;

	@Autowired
	private BaseDataService baseDataService;

	@Autowired
	private EmployeeService employeeService;

	@ResponseBody
	@RequestMapping("/baseDataType")
	public Map<String, Object> selectBaseDataTypeList() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<BaseDataTypeVO> list = baseDataTypeService.getAll();
		map.put(DATA_LIST, list);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 获取所有支付渠道的基础配置
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBaseDataVOList")
	public Map<String, Object> getBaseDataVOList() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<BaseDataVO> list = baseDataService.getValidDataByType(BaseDataType.PAY_CHANNEL, false);
		map.put(DATA_LIST, list);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	@ResponseBody
	@RequestMapping("/user.do")
	public Map<String, Object> user() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<BaseDataVO> allList = baseDataService.getValidAll();
		map.put(BaseDataType.DEGREE.getCode(),
				getBaseDataByType(allList, BaseDataType.DEGREE.getCode()));
		map.put(BaseDataType.FAMILY.getCode(),
				getBaseDataByType(allList, BaseDataType.FAMILY.getCode()));
		map.put(BaseDataType.ASSET.getCode(),
				getBaseDataByType(allList, BaseDataType.ASSET.getCode()));
		map.put(BaseDataType.MARRY.getCode(),
				getBaseDataByType(allList, BaseDataType.MARRY.getCode()));
		map.put("empList", employeeService.findAll());//员工信息
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	public List<BaseDataVO> getBaseDataByType(List<BaseDataVO> allList,
			String type) {
		List<BaseDataVO> list = new ArrayList<BaseDataVO>();
		for (BaseDataVO vo : allList) {
			if (StringUtils.equals(vo.getType(), type)) {
				list.add(vo);
			}
		}
		return list;
	}

	@ResponseBody
	@RequestMapping("/refuseMsg/{type}.do")
	public Map<String, Object> getRefuseMsg(@PathVariable String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<BaseDataVO> allList = baseDataService.getAll();
		map.put(type, getBaseDataByType(allList, type));
		return map;
	}

	@ResponseBody
	@RequestMapping("/getAllEmployee.do")
	public  Map<String, Object> getAllEmployee(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("empList", employeeService.findAll());
		return map;
	}

	@ResponseBody
	@RequestMapping("/assetList")
	public Map<String, Object> getAllAsset(){
		Map<String, Object> map = new HashMap<>();
		List<BaseDataVO> allList = baseDataService.getAll();
		map.put("assetList",
				getBaseDataByType(allList, BaseDataType.ASSET.getCode()));
		return map;
	}

	@ResponseBody
	@RequestMapping("/marryList")
	public Map<String, Object> getAllMarry(){
		Map<String, Object> map = new HashMap<>();
		List<BaseDataVO> allList = baseDataService.getValidAll();
		map.put("marryList",
				getBaseDataByType(allList, BaseDataType.MARRY.getCode()));
		return map;
	}

	@ResponseBody
	@RequestMapping("/interestTabsList")
	public Map<String, Object> interestTabsList(){
		Map<String, Object> map = new HashMap<>();
		List<BaseDataVO> allList = baseDataService.getAll();
		map.put("interestTabsList",
				getBaseDataByType(allList, BaseDataType.INTEREST_TABS.getCode()));
		return map;
	}

	@Autowired
	ProvinceService provinceService;

	@Autowired
	AreaService areaService;

	@ResponseBody
	@RequestMapping("getAllProvince.do")
	public Map<String, Object> getAllProvince(){
		Map<String, Object> map = new HashMap<>();
		map.put("provinceList", provinceService.getAll());
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	@ResponseBody
	@RequestMapping("getCityByProvince/{parentId}.do")
	public Map<String, Object> getCityByProvince(@PathVariable("parentId") Integer parentId){
		Map<String, Object> map = new HashMap<>();
		map.put("areaList", areaService.getAreaByParentId(parentId));
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 获取城市
	 */
	@ResponseBody
	@RequestMapping("/getCitysByName.do")
	public Map<String, Object> getCitysByName(@RequestParam String cityName) {
		Map<String, Object> map = new HashMap<>();
		List<Area> list = areaService.getByCityName(cityName);
		//显示前10条
		if(list.size() > 10) list = list.subList(0, 10);
		map.put(DATA_LIST, list);
		return map;
	}

}
