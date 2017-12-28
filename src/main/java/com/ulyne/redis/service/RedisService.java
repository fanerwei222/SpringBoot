package com.ulyne.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * redis 服务类
 * Created by fanwei_last on 2017/12/28.
 */
@Service
public class RedisService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Resource(name = "stringRedisTemplate")
    ValueOperations<String, String> valueOpeStr;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    ValueOperations<Object, Object> valueOpeObj;

    /**
     * 获取str
     * @param key
     * @return
     */
    public String getStr(String key){
        return valueOpeStr.get(key);
    }

    /**
     * 设置str
     * @param key
     * @param value
     */
    public void setStr(String key, String value){
        valueOpeStr.set(key, value);
    }

    /**
     * 删除str
     * @param key
     */
    public void delStr(String key){
        stringRedisTemplate.delete(key);
    }

    /**
     * 获取object
     * @param object
     * @return
     */
    public Object getObj(Object object){
        return valueOpeObj.get(object);
    }

    /**
     * 设置object
     * @param oKey
     * @param oValue
     */
    public void setObject(Object oKey, Object oValue){
        valueOpeObj.set(oKey, oValue);
    }

    /**
     * 删除object
     * @param key
     */
    public void delObject(Object key){
        redisTemplate.delete(key);
    }
}
