package com.zy_admin.sys.service;



/**
 * redis
 * @author admin
 */
public interface RedisService {

    /**
     *
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     *
     * @param key
     * @return
     */
    Boolean delete(String key);

    /**
     *
     * @param key
     * @return
     */
    Boolean update(String key);

    /**
     *
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