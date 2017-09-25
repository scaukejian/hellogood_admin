package com.hellogood.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hellogood.constant.Code;
import com.hellogood.domain.Login;
import com.hellogood.domain.LoginExample;
import com.hellogood.http.vo.LoginVO;
import com.hellogood.mapper.LoginMapper;

/**
 * Created by kejian on 2017/9/9.
 */
@Service
@Transactional
public class LoginService {

	@Autowired
	LoginMapper loginMapper;

	@Autowired
	UserCacheManager userCacheManager;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 加入黑名单
	 * 
	 * @param userId
	 */
	public void addBlacklist(Integer userId) {
		Login login = getByUserId(userId);
		login.setBlacklist(Code.STATUS_VALID);
		loginMapper.updateByPrimaryKey(login);
	}

	/**
	 * 移除黑名单
	 * 
	 * @param userId
	 */
	public void removeBlacklist(Integer userId) {
		Login login = getByUserId(userId);
		login.setBlacklist(Code.STATUS_INVALID);
		loginMapper.updateByPrimaryKey(login);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Login getByUserId(Integer userId) {
		Login domain = null;
		LoginExample example = new LoginExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<Login> list = loginMapper.selectByExample(example);
		if (!list.isEmpty()) {
			domain = list.get(0);
		}
		return domain;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public LoginVO get(Integer id) {
		LoginVO vo = new LoginVO();
		Login domain = loginMapper.selectByPrimaryKey(id);
		vo.domain2Vo(domain);
		return vo;
	}

	/**
	 * 获取对应用户对应的登录信息的id
	 * 
	 * @param userIds
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Integer> getLoginIdsByUserIds(List<Integer> userIds) {
		LoginExample example = new LoginExample();
		example.createCriteria().andUserIdIn(userIds);
		List<Login> list = loginMapper.selectByExample(example);
		List<Integer>  loginids=new  ArrayList<Integer>();
		for (Login login : list) {
			loginids.add(login.getId());
		}
		return loginids;
	}

	public List<Integer> getBlackUserIds() {
		LoginExample example = new LoginExample();
		example.createCriteria().andBlacklistEqualTo(Code.STATUS_VALID);
		List<Login> logins = loginMapper.selectByExample(example);
		List<Integer> userIds = new ArrayList<Integer>();
		for (Login login : logins) {
			userIds.add(login.getUserId());
		}
		return userIds;
	}

	/**
	 * 获取客户端版本号
	 * @param userId
	 * @return
	 */
	public String getCacheClientVersion(Integer userId){
		String clientVersion = userCacheManager.get(userId, UserCacheManager.UserField.APK_VERSION);
		if(clientVersion == null){
			Login login = getByUserId(userId);
			if(login == null){
				logger.error("找不到用户[userId="+userId+"]账户信息");
			}else {
				clientVersion = login.getApkVersion();
				userCacheManager.updateApkVersion(userId, clientVersion);
			}
		}
		return clientVersion;
	}

	public int getMinaRegisterCount() {
		return loginMapper.getMinaRegisterCount();
	}

}
