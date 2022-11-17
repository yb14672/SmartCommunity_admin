package com.zy_admin.common.core.annotation;

import com.zy_admin.common.enums.BusinessType;
import com.zy_admin.common.enums.OperatorType;

import java.lang.annotation.*;

/**
 * @author yb14672
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLog {
    /**
     * 自定义模块名
     */
    String title() default "";

    /**
     * 方法传入的参数
     * @return
     */
    String optParam() default "";

    /**
     * 操作类型，eg:INSERT, UPDATE...
     * @return
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.MANAGE;
}