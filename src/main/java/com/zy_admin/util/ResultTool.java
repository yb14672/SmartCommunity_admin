package com.zy_admin.util;


import com.zy_admin.common.core.Result.JsonResult;
import com.zy_admin.common.enums.ResultCode;

/**
 * @Author yb14672
 * @Description:
 * @Date Create in
 */
public class ResultTool {
    public static JsonResult success() {
        return new JsonResult(true);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult(true, data);
    }

    public static JsonResult fail() {
        return new JsonResult(false);
    }

    public static JsonResult fail(ResultCode resultEnum) {
        return new JsonResult(false, resultEnum);
    }
}