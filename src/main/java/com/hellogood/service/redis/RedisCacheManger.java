package com.hellogood.service.redis;

import com.hellogood.exception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * Created by kejian on 2017/9/12.
 */
@Service
public class RedisCacheManger{

    public static final int REDIS_CACHE_EXPIRE_DEFAULT = 8 * 60 * 60;

    public static int REDIS_CACHE_EXPIRE_WEEK = 7 * 24 * 60 * 60;

    private Logger logger = Logger.getLogger(RedisCacheManger.class);

    @Autowired
    private JedisPool pool ;

    public <T> T getRedisCacheInfo(String key) {
        logger.info("get from redisCache : " + key);
        Jedis jedis = pool.getResource();
        try {
            return (T)jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            jedis.close();

        }
        return null;
    }

    public <T> boolean setRedisCacheInfo(String key, T value) {
        Jedis jedis = pool.getResource();
        try {
            logger.info("add to redisCache : " + key);
            jedis.set(key, (String) value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }

    /**
     *
     * @param key
     * @param seconds 有效时间(秒为单位)
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean setRedisCacheInfo(String key, int seconds, T value) {
        Jedis jedis = pool.getResource();
        try {
            logger.info("add to redisCache : " + key);
            jedis.setex(key, seconds, (String) value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }

    /**
     * 删除缓存
     * @param key
     * @return
     */
    public  boolean delRedisCacheInfo(String key) {
        Jedis jedis = pool.getResource();
        try {
            logger.info("del to redisCache : " + key);
            if(jedis.exists(key)){
                jedis.del(key);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void publish(String channel, String msg){
    	
    	Jedis jedis = pool.getResource();
        logger.info("channel : " + channel);
        logger.info("msg : " + msg);
        if(StringUtils.isBlank(channel)){
            throw new BusinessException("发布失败，渠道不能为空");
        }
        if(StringUtils.isBlank(msg)){
            throw new BusinessException("发布失败，消息不能为空");
        }
        try {
            jedis.publish(channel, msg);
        } finally {
            jedis.close();
        }
    }

    /**
     * 缓存map对象
     * @param key
     * @param map
     * @param <T>
     * @return
     */
    public <T> boolean addMap(String key, Map map) {
        return addMap(key, map, REDIS_CACHE_EXPIRE_WEEK);
    }

    /**
     * 缓存map
     * @param key
     * @param map
     * @param expireSecond
     * @param <T>
     * @return
     */
    public <T> boolean addMap(String key, Map map, int expireSecond){
        Jedis jedis = pool.getResource();
        try {
            logger.info("addMap : " + key + "; map : " + map);
            jedis.hmset(key, map);
            jedis.expire(key, expireSecond);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }

    /**
     * 缓存Map添加数据
     * @param key
     * @param filed
     * @param <T>
     * @return
     */
    public <T> boolean mapSet(String key, String filed, T value) {
        return mapSet(key, filed, value, REDIS_CACHE_EXPIRE_WEEK);
    }

    /**
     * 缓存Map添加数据
     * @param key
     * @param filed
     * @param <T>
     * @return
     */
    public <T> boolean mapSet(String key, String filed, T value, int expireSecond) {
        Jedis jedis = pool.getResource();
        try {
            logger.info("mapSet : " + key + "; filed : " + filed + "value : " + value);
            //有操作就更新过期时间
            jedis.hset(key, filed, (String) value);
            jedis.expire(key, expireSecond);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }

    /**
     *
     * @param key
     * @return List
     */
    public <T> T getMap(String key) {
        Jedis jedis = pool.getResource();
        try {
            logger.info("getMap : " + key);
            if(exists(key)){
                return (T)jedis.hgetAll(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     *
     * @param key
     * @return List
     */
    public  <T> T getMapValue(String key, String field) {
        Jedis jedis = pool.getResource();
        try {
            logger.info("getMapValue : " + key + "; field : " + field);
            if(exists(key)){
                logger.info("value --》 " + jedis.hget(key, field));
                return (T)jedis.hget(key, field);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * 移除缓存map中的数据
     * @param key
     * @return List
     */
    public  <T> T delMapValue(String key, String field) {
        Jedis jedis = pool.getResource();
        try {
            logger.info("delMapValue : " + key + "; field : " + field);
            if(exists(key)){
                return (T)jedis.hdel(key, field);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    public Boolean exists(String key){
        Boolean flag = false;
        logger.info("exists key " + key);
        Jedis jedis = pool.getResource();
        try {
            flag = jedis.exists(key);
        } finally {
            jedis.close();
        }
        return flag;
    }


    public static void main(String[] args) {
        new RedisCacheManger().setRedisCacheInfo("12345", "asdfg");
    }
}
