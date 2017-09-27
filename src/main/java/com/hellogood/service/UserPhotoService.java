package com.hellogood.service;

import com.hellogood.constant.Code;
import com.hellogood.domain.UserPhoto;
import com.hellogood.domain.UserPhotoExample;
import com.hellogood.exception.BusinessException;
import com.hellogood.http.vo.UserPhotoVO;
import com.hellogood.mapper.UserPhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 用户相册Service
 *
 * @author fzh
 */
@Service
@Transactional
public class UserPhotoService {

    @Autowired
    private UserPhotoMapper userPhotoMapper;

    @Autowired
    private UserCacheManager userCacheManager;

    /**
     * 保存
     *
     * @param userPhoto
     */
    public void update(UserPhoto userPhoto) {
        userPhotoMapper.updateByPrimaryKeySelective(userPhoto);
    }

    /**
     * 新增
     *
     * @param userPhoto
     */
    public void insert(UserPhoto userPhoto) {
        userPhotoMapper.insert(userPhoto);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(Integer id) {
        UserPhoto userPhoto = userPhotoMapper.selectByPrimaryKey(id);
        if (userPhoto == null) {
            throw new BusinessException("照片不存在");
        }
        userPhotoMapper.deleteByPrimaryKey(id);
    }

    public UserPhoto save(String fileName, String filePath, Integer userId) {
        UserPhoto userPhoto = new UserPhoto();
        if (userId != null && userId > 0) {
            UserPhotoExample example = new UserPhotoExample();
            example.createCriteria().andHeadFlagEqualTo(Code.STATUS_VALID).andUserIdEqualTo(userId);
            example.setOrderByClause("update_time desc");
            List<UserPhoto> userPhotoList = userPhotoMapper.selectByExample(example);
            if (!userPhotoList.isEmpty()) userPhoto = userPhotoList.get(0);
            userPhoto.setUserId(userId);
        }
        userPhoto.setHeadFlag(Code.STATUS_VALID);
        userPhoto.setImgName(fileName);
        userPhoto.setOriginalImgName(filePath);
        userPhoto.setUpdateTime(new Date());
        userPhoto.setThumbnailImgName(fileName);
        userPhotoMapper.insert(userPhoto);
        return userPhoto;
    }

    /**
     * 查找数据库用户头像
     * @param @param  userid
     * @param @return
     * @return UserPhoto
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public UserPhotoVO getHeadPhotoByUserId(Integer userId) {
        UserPhotoExample example = new UserPhotoExample();
        example.createCriteria().andHeadFlagEqualTo(Code.STATUS_VALID).andUserIdEqualTo(userId);
        example.setOrderByClause("update_time desc");
        List<UserPhoto> userPhotoList = userPhotoMapper
                .selectByExample(example);
        if (userPhotoList != null && userPhotoList.size() > 0) {
            UserPhotoVO vo = new UserPhotoVO();
            vo.domain2Vo(userPhotoList.get(0));
            return vo;
        }
        return null;
    }


    /**
     * 获取缓存用户头像
     *
     * @param userId
     * @return
     */
    public String getCacheAvatar(Integer userId) {
        String imgName = userCacheManager.get(userId, UserCacheManager.UserField.AVATAR);
        //缓存不存在查询数据库
        if (imgName == null) {
            UserPhotoVO vo = getHeadPhotoByUserId(userId);
            if (vo == null) {
                userCacheManager.updateAvatar(userId, "");
                userCacheManager.updateAvatarThumbnail(userId, "");
            } else {
                imgName = vo.getImgName();
                userCacheManager.updateAvatar(userId, vo.getImgName());
                userCacheManager.updateAvatarThumbnail(userId, vo.getThumbnailImgName());
            }
        }
        return imgName;
    }

    /**
     * 获取缓存头像缩略图
     *
     * @param userId
     * @return
     */
    public String getCacheThumbnailAvatar(Integer userId) {
        String thumbnailImgName = userCacheManager.get(userId, UserCacheManager.UserField.AVATAR_THUMBNAIL);
        //缓存不存在查询数据库
        if (thumbnailImgName == null) {
            UserPhotoVO vo = getHeadPhotoByUserId(userId);
            if (vo == null) {
                userCacheManager.updateAvatar(userId, "");
                userCacheManager.updateAvatarThumbnail(userId, "");
            } else {
                thumbnailImgName = vo.getImgName();
                userCacheManager.updateAvatar(userId, vo.getImgName());
                userCacheManager.updateAvatarThumbnail(userId, vo.getThumbnailImgName());
            }
        }
        return thumbnailImgName;
    }

    /**
     * @param @param  userid
     * @param @return
     * @return UserPhoto
     * @Description: 根据id找出实体
     * @author fukangwen
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public UserPhoto getUserPhoto(Integer id) {
        return userPhotoMapper.selectByPrimaryKey(id);
    }
}
