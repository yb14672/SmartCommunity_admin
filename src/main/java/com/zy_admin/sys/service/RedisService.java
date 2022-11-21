package com.zy_admin.sys.service;



/**
 * redis
 * @author admin
 */
public interface RedisService {

    /**
     *设置缓存
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     *删除指定缓存
     * @param key
     * @return
     */
    Boolean delete(String key);

    /**
     *根据key修改缓存
     * @param key
     * @return
     */
    Boolean update(String key);

    /**
     *根据key获取缓存
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 清空缓存
     * @return
     */
    Boolean empty();
 
}