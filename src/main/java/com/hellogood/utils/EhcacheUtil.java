package com.hellogood.utils;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.net.URL;

/**
 * Created by kejian on 2017/9/17.
 */
public class EhcacheUtil {

    private static final String path = "/ehcache.xml";

    private URL url;

    private CacheManager manager;

    private static EhcacheUtil ehcacheUtil;

    public static EhcacheUtil getInstance(){
        if(ehcacheUtil == null){
            ehcacheUtil = new EhcacheUtil();
        }
        return ehcacheUtil;
    }

    public void put(String cacheName, String key, Object value){
        Cache cache = manager.getCache(cacheName);
        cache.put(key, value);
    }

    public Object get(String cacheName, String key){
        Cache cache = manager.getCache(cacheName);
        return cache.get(key);
    }

    public Object get(String cacheName){
        return manager.getCache(cacheName);
    }

    public void remove(String cacheName, String key){
        manager.getCache(cacheName).evict(key);
    }
}
