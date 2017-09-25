package com.hellogood.service;

import com.hellogood.service.redis.RedisCacheManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kejian on 2017/9/23.
 */
@Component
public class UserCacheManager {

    private static final String PREFIX = "user.";

    @Autowired
    private RedisCacheManger redisCacheManger;


    public void updateBaseInfo(Integer id, String userCode){
        updateBaseInfo(id, userCode, null, null, null);
    }

    /**
     * @param id
     * @param userCode
     * @param userName
     * @param sex
     * @param birthday
     */
    public Map<String, String> updateBaseInfo(Integer id, String userCode, String userName, String sex, String birthday){
        Map<String, String> baseUser = new HashMap<>();
        baseUser.put(UserField.ID.getCode(), id.toString());
        baseUser.put(UserField.USER_CODE.getCode(), userCode);
        baseUser.put(UserField.USER_NAME.getCode(), userName == null ? "" : userName);
        baseUser.put(UserField.SEX.getCode(), sex == null ? "" : sex);
        baseUser.put(UserField.BIRTHDAY.getCode(), birthday == null ? "" : birthday);
        redisCacheManger.addMap(PREFIX + id, baseUser);
        return baseUser;
    }

    public void updateAvatar(Integer id, String avatar){
        redisCacheManger.mapSet(PREFIX + id, UserField.AVATAR.code, avatar == null ? "" : avatar);
    }

    /**
     * 更新用户真实姓名
      * @param id
     * @param certName
     */
    public void updateCertName(Integer id, String certName){
        redisCacheManger.mapSet(PREFIX + id, UserField.CERTNAME.code, certName == null ? "" : certName);
    }

    public void updateDevice(Integer id, String deviceType, String deviceToken, String clientId){
        redisCacheManger.mapSet(PREFIX + id, UserField.DEVICE_TYPE.code, deviceType == null ? "" : deviceType);
        redisCacheManger.mapSet(PREFIX + id, UserField.DEVICE_TOKEN.code, deviceToken == null ? "" : deviceToken);
        redisCacheManger.mapSet(PREFIX + id, UserField.CLIENT_ID.code, clientId == null ? "" : clientId);
    }

    public void updateApkVersion(Integer id, String apkVersion){
        redisCacheManger.mapSet(PREFIX + id, UserField.APK_VERSION.code, apkVersion == null ? "" : apkVersion);
    }

    public void updateAvatarThumbnail(Integer id, String avatarThumbnail){
        redisCacheManger.mapSet(PREFIX + id, UserField.AVATAR_THUMBNAIL.code, avatarThumbnail == null ? "" : avatarThumbnail);
    }


    public String get(Integer userId, UserField userField){
        return redisCacheManger.getMapValue(PREFIX + userId, userField.code);
    }

    public void remove(Integer userId, UserField userField){
        redisCacheManger.delMapValue(PREFIX + userId, userField.code);
    }

    public void remove(Integer userId, UserField... userFields){
        for (UserField userField : userFields){
            redisCacheManger.delMapValue(PREFIX + userId, userField.code);
        }
    }

    public Map<String, String> get(Integer userId){
        return redisCacheManger.getMap(PREFIX + userId);
    }

    public enum UserField{
        ID("id", "ID"),
        USER_CODE("userCode", "易悦号"),
        USER_NAME("userName", "用户名"),
        CERTNAME("certName", "真实姓名"),
        SEX("sex", "性别"),
        BIRTHDAY("birthday", "生日"),
        AVATAR("avatar", "头像"),
        AVATAR_THUMBNAIL("avatarThumbnail", "头像"),
        APK_VERSION("apkVersion", "客户端版本"),
        USE_IOS_NEW_CERT("useIosNewCert", "使用IOS新证书"),
        DEVICE_TYPE("deviceType", "设备类型"),
        DEVICE_TOKEN("deviceToken", "IOS设备token"),
        CLIENT_ID("clientId", "Android设备ID");

        private String code;
        private String description;

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        UserField(String code, String description) {
            this.code = code;
            this.description = description;
        }
    }

}
