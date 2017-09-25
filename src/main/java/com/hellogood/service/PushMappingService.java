package com.hellogood.service;

import com.hellogood.domain.PushMapping;
import com.hellogood.domain.PushMappingExample;
import com.hellogood.http.vo.PushMappingVO;
import com.hellogood.mapper.PushMappingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 个推和用户映射表Service
 * @author kejian
 *
 */
@Service
@Transactional
public class PushMappingService {

	@Autowired
	private PushMappingMapper pushMappingMapper;

	@Autowired
	private UserCacheManager userCacheManager;

	/**
	 * @Description: 根据userId，返回ClientId
	 * @param @param userId
	 * @param @return
	 * @return String
	 * @author fukangwen
	 */
	public String getClientId(Integer userId) {
		PushMappingExample example = new PushMappingExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<PushMapping> pushMappingList = pushMappingMapper.selectByExample(example);
		if(pushMappingList != null && pushMappingList.size() > 0)
			return pushMappingList.get(0).getClientId();
		return null;
	}
	
	/**
	 * @Description: 根据userId，返回pushMapping
	 * @param @param userId
	 * @param @return
	 * @return String
	 * @author fukangwen
	 */
	public PushMapping getPushMapping(Integer userId) {
		PushMappingExample example = new PushMappingExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<PushMapping> pushMappingList = pushMappingMapper.selectByExample(example);
		if(pushMappingList != null && pushMappingList.size() > 0)
			return pushMappingList.get(0);
		return null;
	}


	/**
	 * @Description: 根据userId，返回pushMappingVO
	 * @param @param userId
	 * @param @return
	 * @return String
	 */
	public PushMappingVO getCachePushMapping(Integer userId) {
		PushMappingVO pushMappingVO = null;
		String deviceType = userCacheManager.get(userId, UserCacheManager.UserField.DEVICE_TYPE);
		String deviceToken = userCacheManager.get(userId, UserCacheManager.UserField.DEVICE_TOKEN);
		String clientId = userCacheManager.get(userId, UserCacheManager.UserField.CLIENT_ID);
		//缓存为空,查询数据表
		if(deviceType == null){
			PushMapping pushMapping = getPushMapping(userId);
			if(pushMapping != null){
				deviceType = pushMapping.getDeviceName();
				deviceToken = pushMapping.getDeviceToken();
				clientId = pushMapping.getClientId();
			}
			//更新缓存
			userCacheManager.updateDevice(userId, deviceType, deviceToken, clientId);
		}

		pushMappingVO = new PushMappingVO(userId, deviceType, clientId, deviceToken);

		return pushMappingVO;
	}


	/**
	 * @Description: 根据userId，返回pushMapping
	 * @param @param userId
	 * @param @return
	 * @return String
	 * @author fukangwen
	 */
	public PushMapping getPushMappingByCid(String clientId) {
		PushMappingExample example = new PushMappingExample();
		example.createCriteria().andClientIdEqualTo(clientId);
		List<PushMapping> pushMappingList = pushMappingMapper.selectByExample(example);
		if(pushMappingList != null && pushMappingList.size() > 0)
			return pushMappingList.get(0);
		return null;
	}
	
	/**
	 * @Description: 根据deviceToken，返回pushMapping
	 * @param @param userId
	 * @param @return
	 * @return String
	 * @author fukangwen
	 */
	public PushMapping getPushMappingByDeviceToken(String deviceToken) {
		PushMappingExample example = new PushMappingExample();
		example.createCriteria().andDeviceTokenEqualTo(deviceToken);
		List<PushMapping> pushMappingList = pushMappingMapper.selectByExample(example);
		if(pushMappingList != null && pushMappingList.size() > 0)
			return pushMappingList.get(0);
		return null;
	}
	/**
	 * @Description: 根据userId，返回DeviceToken
	 * @param @param userId
	 * @param @return
	 * @return String
	 * @author fukangwen
	 */
	public String getDeviceToken(Integer userId) {
		PushMappingExample example = new PushMappingExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<PushMapping> pushMappingList = pushMappingMapper.selectByExample(example);
		if(pushMappingList != null && pushMappingList.size() > 0)
			return pushMappingList.get(0).getDeviceToken();
		return null;
	}
	
	/**
	 * @Description: 根据userId集合，返回ClientId集合
	 * @param @param userId
	 * @param @return
	 * @return String
	 * @author fukangwen
	 */
	//TODO
	public List<String> getClientIdList(List<Integer> userIdList) {
		if(userIdList == null || userIdList.size() < 1)
			return null;
		List<String> clientIdList = new ArrayList<String>();
		for(Integer userId : userIdList){
			clientIdList.add(this.getClientId(userId));
		}
		return clientIdList;
	}
	
	/**
	 * 返回映射表的全部数据,用逗号隔开
	 * @return
	 */
	public String getAllUserIdStringList(){
		PushMappingExample example = new PushMappingExample();
		List<PushMapping> pushMappingList = pushMappingMapper.selectByExample(example);
		if(pushMappingList == null || pushMappingList.size() < 1)
			return "";
		StringBuffer strBuf = new StringBuffer();
		for(PushMapping pushMapping : pushMappingList){
			strBuf.append(pushMapping.getUserId() + ",");
		}
		return strBuf.toString();
	}
	
	/**
	 * @Description: 新增一条记录
	 * @param @param pushMapping
	 * @return void
	 * @author fukangwen
	 */
	public void insert(PushMapping pushMapping){
		pushMappingMapper.insert(pushMapping);
	}
	
	/**
	 * @Description: 更新一条记录
	 * @param @param pushMapping
	 * @return void
	 * @author fukangwen
	 */
	public void update(PushMapping pushMapping){
		pushMappingMapper.updateByPrimaryKey(pushMapping);
	}

}
