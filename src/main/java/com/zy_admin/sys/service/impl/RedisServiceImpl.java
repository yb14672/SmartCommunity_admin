package com.zy_admin.sys.service.impl;


import com.zy_admin.sys.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
 
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 设置key并且设置有效时间
     * @param key
     * @param value
     */
    @Override
    public void set(String key, Object value) {
        //更改在redis里面查看key编码问题
        RedisSerializer redisSerializer =new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        //设置key并且设置有效时间
        vo.set(key, value,5, TimeUnit.SECONDS);
    }

    /**
     * 删除key
     * @param key
     * @return
     */
    @Override
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 更新key
     * @param key
     * @return
     */
    @Override
    public  Boolean update(String key){
        return redisTemplate.expire(key, 5, TimeUnit.SECONDS);
    }

    /**
     * 查询key
     * @param key
     * @return
     */
    @Override
    public Object get(String key) {
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        //验证有效时间
        Long expire = redisTemplate.boundHashOps(key).getExpire();
        System.err.println("redis有效时间："+expire+"S");
        return vo.get(key);
    }
}