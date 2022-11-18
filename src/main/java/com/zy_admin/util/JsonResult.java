package com.zy_admin.util;



import java.io.Serializable;

/**
 * @Author yb14672
 * @Description: 统一返回实体
 * @Date Create in
 */
public class JsonResult<T> implements Serializable {
    private Boolean success;
    private Integer errorCode;
    private String errorMsg;
    private T data;

    public JsonResult() {
    }

    public JsonResult(boolean success) {
        this.success = success;
        this.errorCode = success ? com.zy_admin.util.ResultCode.SUCCESS.getCode() : com.zy_admin.util.ResultCode.COMMON_FAIL.getCode();
        this.errorMsg = success ? com.zy_admin.util.ResultCode.SUCCESS.getMessage() : com.zy_admin.util.ResultCode.COMMON_FAIL.getMessage();
    }

    public JsonResult(boolean success, com.zy_admin.util.ResultCode resultEnum) {
        this.success = success;
        this.errorCode = success ? com.zy_admin.util.ResultCode.SUCCESS.getCode() : (resultEnum == null ? com.zy_admin.util.ResultCode.COMMON_FAIL.getCode() : resultEnum.getCode());
        this.errorMsg = success ? com.zy_admin.util.ResultCode.SUCCESS.getMessage() : (resultEnum == null ? com.zy_admin.util.ResultCode.COMMON_FAIL.getMessage() : resultEnum.getMessage());
    }

    public JsonResult(boolean success, T data) {
        this.success = success;
        this.errorCode = success ? com.zy_admin.util.ResultCode.SUCCESS.getCode() : com.zy_admin.util.ResultCode.COMMON_FAIL.getCode();
        this.errorMsg = success ? com.zy_admin.util.ResultCode.SUCCESS.getMessage() : com.zy_admin.util.ResultCode.COMMON_FAIL.getMessage();
        this.data = data;
    }

    public JsonResult(boolean success, com.zy_admin.util.ResultCode resultEnum, T data) {
        this.success = success;
        this.errorCode = success ? com.zy_admin.util.ResultCode.SUCCESS.getCode() : (resultEnum == null ? com.zy_admin.util.ResultCode.COMMON_FAIL.getCode() : resultEnum.getCode());
        this.errorMsg = success ? com.zy_admin.util.ResultCode.SUCCESS.getMessage() : (resultEnum == null ? com.zy_admin.util.ResultCode.COMMON_FAIL.getMessage() : resultEnum.getMessage());
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
