package com.hellogood.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hellogood.constant.Code;
import com.hellogood.domain.BaseData;
import com.hellogood.domain.BaseDataExample;
import com.hellogood.enumeration.BaseDataType;
import com.hellogood.exception.BusinessException;
import com.hellogood.http.vo.BaseDataVO;
import com.hellogood.mapper.BaseDataMapper;
import com.hellogood.mapper.BaseDataTypeMapper;
import com.hellogood.service.redis.RedisCacheManger;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 基础数据服务 Created by kejian on 2017/9/21.
 */
@Service
@Transactional
public class BaseDataService {

	private Logger logger = LoggerFactory.getLogger(BaseDataService.class);

	public static Map<Integer, String> baseDataMap = new HashMap<Integer, String>();

	@Autowired
	private BaseDataMapper baseDataMapper;

	@Autowired
	private BaseDataTypeMapper baseDataTypeMapper;
	
	@Autowired
	private RedisCacheManger redisCacheManger;

	private Gson gson = new Gson();

	@PostConstruct
	public void init() {
		// 基础数据
		List<BaseData> list = baseDataMapper
				.selectByExample(new BaseDataExample());
		for (BaseData data : list) {
			baseDataMap.put(data.getId(), data.getName());
		}
	}

	/**
	 * 检查参数是否为空
	 * @param vo
	 */
	public void checkParam(BaseDataVO vo) {
		if (StringUtils.isBlank(vo.getType()))
			throw new BusinessException("基础数据类型不能为空");
		if (StringUtils.isBlank(vo.getCode()))
			throw new BusinessException("基础数据编号不能为空");
	}

	/**
	 * @param vo
	 * @return
	 */
	public void add(BaseDataVO vo) {
		checkParam(vo); //检查参数是否为空
		BaseDataExample example = new BaseDataExample();
		BaseDataExample.Criteria criteria = example.createCriteria();
		criteria.andCodeEqualTo(vo.getCode());
		List<BaseData> baseDataList = baseDataMapper.selectByExample(example);
		if (!baseDataList.isEmpty())
			throw new BusinessException("已存在该编号基础数据");
		BaseData baseData = new BaseData();
		vo.vo2Domain(baseData);
		baseData.setStatus(1);
		baseData.setCreateTime(new Date());
		baseData.setUpdateTime(new Date());
		baseDataMapper.insert(baseData);
		//更新redis缓存
		getValidDataByType(baseData.getType(), true);
	}

	/**
	 * @param vo
	 * @return
	 */
	public void update(BaseDataVO vo) {
		checkParam(vo); //检查参数是否为空
		if (vo.getId() == null)
			throw new BusinessException("获取参数失败");
		BaseData oldBaseData = baseDataMapper.selectByPrimaryKey(vo.getId());
		if (oldBaseData == null || StringUtils.isBlank(oldBaseData.getCode()))
			throw new BusinessException("获取数据失败");
		String oldCode = oldBaseData.getCode();
		String newCode = vo.getCode();
		if (!newCode.equals(oldCode)) {
			BaseDataExample example = new BaseDataExample();
			BaseDataExample.Criteria criteria = example.createCriteria();
			criteria.andCodeEqualTo(newCode);
			List<BaseData> baseDataList = baseDataMapper.selectByExample(example);
			if (!baseDataList.isEmpty())
				throw new BusinessException("已存在该编号基础数据");
		}

		BaseData baseData = new BaseData();
		vo.vo2Domain(baseData);
		baseData.setUpdateTime(new Date());
		baseDataMapper.updateByPrimaryKeySelective(baseData);
		//更新redis缓存
		getValidDataByType(baseData.getType(), true);
	}

	/**
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public BaseDataVO get(Integer id) {
		if (id == null) {
			throw new BusinessException("id不能为空");
		}
		BaseData baseData = baseDataMapper.selectByPrimaryKey(id);
		BaseDataVO vo = new BaseDataVO();
		vo.domain2Vo(baseData);
		return vo;
	}

	/**
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<BaseDataVO> getAll() {
		List<BaseDataVO> list = new ArrayList<BaseDataVO>();
		List<BaseData> baseDatas = baseDataMapper
				.selectByExample(new BaseDataExample());
		for (BaseData domain : baseDatas) {
			BaseDataVO vo = new BaseDataVO();
			vo.domain2Vo(domain);
			list.add(vo);
		}
		return list;
	}

	/**
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<BaseDataVO> getValidAll() {
		List<BaseDataVO> list = new ArrayList<>();
		BaseDataExample example = new BaseDataExample();
		example.createCriteria().andStatusEqualTo(Code.STATUS_VALID);
		List<BaseData> baseDatas = baseDataMapper
				.selectByExample(example);
		for (BaseData domain : baseDatas) {
			BaseDataVO vo = new BaseDataVO();
			vo.domain2Vo(domain);
			list.add(vo);
		}
		return list;
	}

	/**
	 * 
	 * @param baseDataVO
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public PageInfo pageJson(BaseDataVO baseDataVO, int page, int pageSize) {
		// 分页
		PageHelper.startPage(page, pageSize);
		BaseDataExample example = new BaseDataExample();
		List<BaseDataVO> listvo = new ArrayList<BaseDataVO>();
		// 条件查询
		BaseDataExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(baseDataVO.getName())) {
			// 模糊查询
			criteria.andNameLike(MessageFormat.format("%{0}%",
					baseDataVO.getName()));
		}
		if (StringUtils.isNotBlank(baseDataVO.getCode())) {
			// 模糊查询
			criteria.andCodeLike(MessageFormat.format("%{0}%",
					baseDataVO.getCode()));
		}
		if (StringUtils.isNotBlank(baseDataVO.getType())) {
			// 模糊查询
			criteria.andTypeLike(MessageFormat.format("%{0}%",
					baseDataVO.getType()));
		}
		// 基础数据使用状态 0 作废 1 使用中 9999为请选择：没有选择状态
		if (baseDataVO.getStatus() != null && baseDataVO.getStatus() != 9999) {
			criteria.andStatusEqualTo(baseDataVO.getStatus());
		}
		List<BaseData> baseDatas = baseDataMapper.selectByExample(example);
		PageInfo pageInfo = new PageInfo(baseDatas);
		for (BaseData baseData : baseDatas) {
			BaseDataVO vo = new BaseDataVO();
			vo.domain2Vo(baseData);
			com.hellogood.domain.BaseDataType baseDataType = baseDataTypeMapper
					.selectByType(baseData.getType());
			if (baseDataType != null)
				vo.setType(baseDataType.getName());
			listvo.add(vo);
		}
		pageInfo.getList().clear();
		pageInfo.getList().addAll(listvo);
		return pageInfo;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<BaseDataVO> getAllByIds(List<Integer> baseMealIds) {
		List<BaseDataVO> baseDataVOList = new ArrayList<BaseDataVO>();
		BaseDataExample example = new BaseDataExample();
		BaseDataExample.Criteria criteria = example.createCriteria();
		if (baseMealIds != null && baseMealIds.size() > 0)
			criteria.andIdIn(baseMealIds);
		criteria.andTypeEqualTo("meal");
		List<BaseData> baseDataList = baseDataMapper.selectByExample(example);
		for (BaseData baseData : baseDataList) {
			BaseDataVO vo = new BaseDataVO();
			vo.domain2Vo(baseData);
			baseDataVOList.add(vo);
		}
		return baseDataVOList;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<BaseDataVO> getAllByType(String type) {
		List<BaseDataVO> list = new ArrayList<BaseDataVO>();
		BaseDataExample example = new BaseDataExample();
		example.createCriteria().andTypeEqualTo(type);
		List<BaseData> baseDatas = baseDataMapper.selectByExample(example);
		for (BaseData domain : baseDatas) {
			BaseDataVO vo = new BaseDataVO();
			vo.domain2Vo(domain);
			list.add(vo);
		}
		return list;
	}


	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<BaseDataVO> getAllByType(String type,Integer status) {
		List<BaseDataVO> list = new ArrayList<BaseDataVO>();
		BaseDataExample example = new BaseDataExample();
		BaseDataExample.Criteria criteria = example.createCriteria();
		criteria.andTypeEqualTo(type);
		criteria.andStatusEqualTo(status);
		List<BaseData> baseDatas = baseDataMapper.selectByExample(example);
		for (BaseData domain : baseDatas) {
			BaseDataVO vo = new BaseDataVO();
			vo.domain2Vo(domain);
			list.add(vo);
		}
		return list;
	}

	public boolean modifyStatusById(Integer id, Integer status) {
		BaseData baseData = baseDataMapper.selectByPrimaryKey(id);
		if (baseData != null) {
			baseData.setStatus(status);
			baseDataMapper.updateByPrimaryKeySelective(baseData);
			//更新redis缓存
			getValidDataByType(baseData.getType(), true);
			return true;
		}
		return false;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public BaseData getDataByTypeAndCode(String type, String code){
		if (StringUtils.isBlank(type) || StringUtils.isBlank(code)) return null;
        BaseData baseData = new BaseData();
        BaseDataExample example = new BaseDataExample();
        example.createCriteria().andTypeEqualTo(type).andCodeEqualTo(code);
        List<BaseData> list = baseDataMapper.selectByExample(example);
        if(!list.isEmpty()){
            baseData = list.get(0);
        }
        return baseData;
    }


	/**
	 * 按类型缓存
	 * @param type
	 * @refresh 是否刷新缓存
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<BaseDataVO> getValidDataByType(BaseDataType type, boolean refresh) {
		return getValidDataByType(type.getCode(), refresh);
	}

	/**
	 * 按类型缓存
	 * @param type
	 * @refresh 是否刷新缓存
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<BaseDataVO> getValidDataByType(String type, boolean refresh) {
		List<BaseDataVO> list = new ArrayList<>();
		String baseDataCacheKey = BaseData.class.getSimpleName() + "_" + type;
		String jsonStr = null;
		//是否刷新缓存
		if(false == refresh){
			jsonStr = redisCacheManger.getRedisCacheInfo(baseDataCacheKey);
		}
		if(StringUtils.isNotBlank(jsonStr)){
			list = gson.fromJson(jsonStr, new TypeToken<List<BaseDataVO>>() {}.getType());
		}else {
			BaseDataExample example = new BaseDataExample();
			example.createCriteria().andTypeEqualTo(type).andStatusEqualTo(Code.STATUS_VALID);
			List<BaseData> baseDatas = baseDataMapper.selectByExample(example);
			for (BaseData domain : baseDatas) {
				BaseDataVO vo = new BaseDataVO();
				vo.domain2Vo(domain);
				list.add(vo);
			}
			redisCacheManger.setRedisCacheInfo(baseDataCacheKey, RedisCacheManger.REDIS_CACHE_EXPIRE_DEFAULT, gson.toJson(list));
		}
		return list;
	}

	public List<BaseDataVO> getAllByIdsAndType(List<Integer> baseIds,
			String type) {
		List<BaseDataVO> baseDataVOList = new ArrayList<BaseDataVO>();
		BaseDataExample example = new BaseDataExample();
		BaseDataExample.Criteria criteria = example.createCriteria();
		if (baseIds != null && baseIds.size() > 0)
			criteria.andIdIn(baseIds);
		criteria.andTypeEqualTo(type);
		List<BaseData> baseDataList = baseDataMapper.selectByExample(example);
		for (BaseData baseData : baseDataList) {
			BaseDataVO vo = new BaseDataVO();
			vo.domain2Vo(baseData);
			baseDataVOList.add(vo);
		}
		return baseDataVOList;
	}

	/**
	 * 获取所有支付渠道的基础配置
	 * @return
	 */
	public List<BaseDataVO> getBaseDataVOList() {
		return getValidDataByType(BaseDataType.PAY_CHANNEL, false);
	}

	/**
	 * 根据支付渠道代码获取支付名称
	 * @param payChannel
	 * @return
	 */
	public String getPayChannelName(String payChannel) {
		if (StringUtils.isBlank(payChannel)) return "";
		BaseData baseData = getDataByTypeAndCode(BaseDataType.PAY_CHANNEL.getCode(), payChannel);
		if (baseData == null || StringUtils.isBlank(baseData.getName())){
			return "";
		}
		return baseData.getName();
	}
}
