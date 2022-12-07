package com.zy_admin.sys.service.impl;

import com.zy_admin.sys.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis服务层impl
 *
 * @author admin
 * @date 2022/11/22
 */
@Service
public class RedisServiceImpl implements RedisService {
    /**
     * 服务对象
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置缓存
     *
     * @param key   Token
     * @param value 用户名
     */
    @Override
    public void set(String key, Object value) {
        //更改在redis里面查看key编码问题
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        //设置key并且设置有效时间
        vo.set(key, value, 5, TimeUnit.SECONDS);
    }

    /**
     * 删除缓存
     *
     * @param key Token
     * @return 成功或失败的结果集
     */
    @Override
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 更新缓存
     *
     * @param key Token
     * @return 成功或失败的结果集
     */
    @Override
    public Boolean update(String key) {
        return redisTemplate.expire(key, 30, TimeUnit.MINUTES);
    }

    /**
     * 获取缓存
     *
     * @param key Token
     * @return 缓存对象
     */
    @Override
    public Object get(String key) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        //验证有效时间
        Long expire = redisTemplate.boundHashOps(key).getExpire();
        if (expire <= 0) {
            return null;
        }
        return vo.get(key);
    }

    /**
     * 清空缓存
     *
     * @return 成功或失败的结果集
     */
    @Override
    public Boolean empty() {
        try {
            Set<String> keys = redisTemplate.keys("*");
            if (keys != null) {
                redisTemplate.delete(keys);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}