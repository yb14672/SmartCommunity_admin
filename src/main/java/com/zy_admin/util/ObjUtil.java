package com.zy_admin.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Field;

/**
 * 扩展hutool的ObjectUtil
 * @author yb14672
 * Time:2022/11/18 - 10:35
 **/
public class ObjUtil extends ObjectUtil {

    /**
     * 比较两个对象的指定属性是否相等
     * @param newObj 对象1
     * @param oldObj 对象2
     * @param fields   需要比较的属性
     * @return
     */
    public static <T> Boolean checkEquals(T newObj, T oldObj,String... fields) {
        //判断两个对象是否为空
        if(ObjectUtil.isNotNull(newObj)&&ObjectUtil.isNotNull(oldObj)){
            Field[] fields1 = ReflectUtil.getFields(newObj.getClass(), field -> true);
            System.out.println(fields1);
        }
        return true;
    };
}
