package service;

import com.hellogood.constant.PushConstants;
import com.hellogood.http.vo.MessageVO;
import com.hellogood.service.MessageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created by kejian on 2017/12/4.
 */
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
public class MessageServiceTest extends ServiceTestBase{

    @Autowired
    private MessageService messageService;

    @Test
    public void save(){
        MessageVO message = new MessageVO();
        message.setPushType(PushConstants.PUSH_TYPE_SINGLE);
        message.setContent("测试推送113111");
        message.setType(6);
        message.setUserId(1105);

        messageService.save(message);
    }
}
