package service;

import com.hellogood.service.redis.RedisCacheManger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by kejian on 2017/10/28.
 */
public class RedisCacheMangerTest extends ServiceTestBase{
    @Autowired
    RedisCacheManger redisCacheManger;

    @Test
    public void get(){
        String str = redisCacheManger.getRedisCacheInfo("BaseData_smsChannel");
        System.out.println(str);
    }
}
