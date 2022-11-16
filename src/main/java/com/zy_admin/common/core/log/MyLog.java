package com.zy_admin.common.core.log;

import java.lang.annotation.*;

/**
 * @author yb14672
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLog {
    // 自定义模块名，eg:登录
    String title() default "";
    // 方法传入的参数
    String optParam() default "";
    // 操作类型，eg:INSERT, UPDATE...
    BusinessType businessType() default BusinessType.OTHER;
}