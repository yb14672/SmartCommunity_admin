package com.zy_admin.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 扩展hutool的ObjectUtil
 *
 * @author yb14672
 * Time:2022/11/18 - 10:35
 **/
public class ObjUtil extends ObjectUtil {

    /**
     * 比较两个对象的指定属性是否相等
     *
     * @param newObj 对象1
     * @param oldObj 对象2
     * @param fields 需要比较的属性
     */
    public static <T> Boolean checkEquals(T newObj, T oldObj, String[] fields) {
        //判断两个对象是否为空
        if (ObjectUtil.isNotNull(newObj) && ObjectUtil.isNotNull(oldObj)) {
            Map<String, Object> newObjValues = getFieldValueMap(newObj, fields);
            Map<String, Object> oldObjValues = getFieldValueMap(oldObj, fields);
            //循环遍历对应属性是否相等
            for (String s : oldObjValues.keySet()) {
                Object newObjValue = newObjValues.get(s);
                Object oldObjValue = oldObjValues.get(s);
                //如果不等则两对象不同，返回false
                if (!ObjectUtil.equals(oldObjValue,newObjValue)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 根据属性名获取对应类中的值
     *
     * @param obj    要获取的类
     * @param fields 属性列表
     */
    public static <T> Map<String, Object> getFieldValueMap(T obj, String[] fields) {
        HashMap<String, Object> hashMap = new HashMap<>();
        //循环遍历需要查询的字段名
        for (String fieldName : fields) {
            //获取对应属性名的值
            Object fieldValue = ReflectUtil.getFieldValue(obj, fieldName);
            //将其添加到HashMap中
            hashMap.put(fieldName, fieldValue);
        }
        return hashMap;
    }
}
