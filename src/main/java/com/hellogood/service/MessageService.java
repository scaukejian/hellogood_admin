package com.hellogood.service;

import com.hellogood.constant.Code;
import com.hellogood.constant.PushConstants;
import com.hellogood.domain.Message;
import com.hellogood.enumeration.BaseDataType;
import com.hellogood.exception.BusinessException;
import com.hellogood.http.vo.BaseDataVO;
import com.hellogood.http.vo.MessageVO;
import com.hellogood.http.vo.PushMappingVO;
import com.hellogood.mapper.MessageMapper;
import com.hellogood.service.redis.RedisCacheManger;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by kejian
 */
@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private BaseDataService baseDataService;

    @Autowired
    private RedisCacheManger redisCacheManger;

    @Autowired
    private LoginService loginService;
    @Autowired
    private PushMappingService pushMappingService;


    /**
     * 发布渠道
     */
    public static final String PUSH_CHANNEL = "message";

    /**
     * json转换类
     */
    public static Gson gson = new Gson();


    public static org.slf4j.Logger logger = LoggerFactory.getLogger(MessageService.class);

    /**
     * 检查消息
     * @param messageVO
     */
    private void check(MessageVO messageVO){
        if(messageVO.getUserId() == null){
            throw new BusinessException("用户ID不能为空！");
        }
        /*UserVO userInfo = userService.getUserBaseInfo(messageVO.getUserId());
        if(userInfo == null){
            throw new BusinessException("该用户不存在！");
        }*/
        if(messageVO.getType() == null){
            throw new BusinessException("发送类型不能为空！");
        }
    }

    /**
     * type:消息类型：
     *               3.审核通过
     *               9.认证拒绝
     *               10.用户资料审核通过
     *               11. 用户资料审核退回'
     * senderInfoId：根据类型不同存储发送人的userId或者邀请函的id,或者orderId等等
     * userId:用户id
     * @param messageVO
     */
    public void save(MessageVO messageVO) {
        logger.info("消息处理：" + messageVO);
        //检查消息
        check(messageVO);
        // 组装消息内容(如果内容为空，再根据类型进行组装)
        if (messageVO.getContent() == null) {
            messageVO.setContent(this.getContentByMessage(messageVO));
        }
       /* if (StringUtils.isBlank(messageVO.getPhone()))
            messageVO.setPhone(userService.getUserBaseInfo(messageVO.getUserId()).getPhone());*/
        Message domain = new Message();
        //如果消息类型不是短信通知，则需要拿到客户端类型信息，否则直接发送短信即可
        PushMappingVO pushMapping = pushMappingService.getCachePushMapping(messageVO.getUserId());
        if (pushMapping == null || StringUtils.isBlank(pushMapping.getDeviceName())) {//没有设备信息，不推送直接保存
            messageVO.vo2Domain(domain);
            save(domain);
            return;
        }
        //判断用户的设备类型
        if (PushConstants.PUSH_DEVICE_ANDROID.equals(pushMapping.getDeviceName())) {
            messageVO.setClientType(PushConstants.PUSH_DEVICE_ANDROID);
            messageVO.setClientId(pushMapping.getClientId());
        } else {
            messageVO.setClientType(PushConstants.PUSH_DEVICE_IOS);
            messageVO.setDeviceToken(pushMapping.getDeviceToken());
        }
        //易悦精英客户端标识
        generateClientProVersionFlag(messageVO);
        messageVO.vo2Domain(domain);
        save(domain);
        messageVO.setId(domain.getId());
        logger.info("发布消息：" + messageVO);
        //发布消息
        redisCacheManger.publish(PUSH_CHANNEL, gson.toJson(messageVO));
    }

    private void save(Message domain){
        domain.setCreateTime(new Date());
        domain.setStatus(Code.STATUS_VALID);
        domain.setValidStatus(Code.STATUS_VALID);
        messageMapper.insert(domain);
    }

    /**
     * 是否为IOS易悦精英客户端
     * @param messageVO
     * @return
     */
    public void generateClientProVersionFlag(MessageVO messageVO){
        if(!"IOS".equals(messageVO.getClientType())){
            return;
        }
        String clientVersion = loginService.getCacheClientVersion(messageVO.getUserId());
        //是否为易悦精英客户端 ios-4.0.0以上
        if(StringUtils.isNotBlank(clientVersion) && Integer.valueOf(StringUtils.remove(clientVersion, ".")) >= 400){
            messageVO.setClientProVersionFlag(Code.STATUS_VALID);
        }
    }

    /**
     * 根据类型重组发送内容
     * @param message
     * @return
     */
    public String getContentByMessage(MessageVO message) {
        BaseDataVO baseData = null;
        //code为MESSAGE
        List<BaseDataVO> list = baseDataService.getValidDataByType(BaseDataType.MESSAGE, false);
        for (BaseDataVO vo : list){
            if(StringUtils.equals(message.getType().toString(), vo.getCode())){
                baseData = vo;
                break;
            }
        }
        return baseData == null ? null : baseData.getName();
    }

}
