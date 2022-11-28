package com.zy_admin.sys.service;

/**
 * redis缓存
 * @author admin
 */
public interface RedisService {
    /**
     * 设置缓存
     * @param key  Token
     * @param value 用户名
     */
    void set(String key, Object value);

    /**
     *删除缓存
     * @param key  Token
     * @return 成功或失败的结果集
     */
    Boolean delete(String key);

    /**
     *更新缓存
     * @param key  Token
     * @return 成功或失败的结果集
     */
    Boolean update(String key);

    /**
     * 获取缓存
     * @param key Token
     * @return 缓存对象
     */
    Object get(String key);

    /**
     * 清空缓存
     * @return 成功或失败的结果集
     */
    Boolean empty();
 
}