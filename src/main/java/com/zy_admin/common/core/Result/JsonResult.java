package com.zy_admin.common.core.Result;


import com.zy_admin.common.enums.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 将结果转为JSON格式
 *
 * @Author yb14672
 * @Description: 统一返回实体
 * @Date Create in
 */
@ApiModel(description = "将结果转为JSON格式")
public class JsonResult<T> implements Serializable {
    /**
     * 是否成功
     */
    @ApiModelProperty("是否成功")
    private Boolean success;
    /**
     * 返回代码
     */
    @ApiModelProperty("返回代码")
    private Integer errorCode;
    /**
     * 返回信息
     */
    @ApiModelProperty("返回信息")
    private String errorMsg;
    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private T data;

    /**
     * json结果
     */
    public JsonResult() {
    }

    /**
     * json结果
     *
     * @param success 成功
     */
    public JsonResult(boolean success) {
        this.success = success;
        this.errorCode = success ? ResultCode.SUCCESS.getCode() : ResultCode.COMMON_FAIL.getCode();
        this.errorMsg = success ? ResultCode.SUCCESS.getMessage() : ResultCode.COMMON_FAIL.getMessage();
    }

    /**
     * json结果
     *
     * @param success    成功
     * @param resultEnum 结果枚举
     */
    public JsonResult(boolean success, ResultCode resultEnum) {
        this.success = success;
        this.errorCode = success ? ResultCode.SUCCESS.getCode() : (resultEnum == null ? ResultCode.COMMON_FAIL.getCode() : resultEnum.getCode());
        this.errorMsg = success ? ResultCode.SUCCESS.getMessage() : (resultEnum == null ? ResultCode.COMMON_FAIL.getMessage() : resultEnum.getMessage());
    }

    /**
     * json结果
     *
     * @param success 成功
     * @param data    数据
     */
    public JsonResult(boolean success, T data) {
        this.success = success;
        this.errorCode = success ? ResultCode.SUCCESS.getCode() : ResultCode.COMMON_FAIL.getCode();
        this.errorMsg = success ? ResultCode.SUCCESS.getMessage() : ResultCode.COMMON_FAIL.getMessage();
        this.data = data;
    }

    /**
     * json结果
     *
     * @param success    成功
     * @param resultEnum 结果枚举
     * @param data       数据
     */
    public JsonResult(boolean success, ResultCode resultEnum, T data) {
        this.success = success;
        this.errorCode = success ? ResultCode.SUCCESS.getCode() : (resultEnum == null ? ResultCode.COMMON_FAIL.getCode() : resultEnum.getCode());
        this.errorMsg = success ? ResultCode.SUCCESS.getMessage() : (resultEnum == null ? ResultCode.COMMON_FAIL.getMessage() : resultEnum.getMessage());
        this.data = data;
    }

    /**
     * 获得成功
     *
     * @return {@link Boolean}
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 设置成功
     *
     * @param success 成功
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * 得到错误代码
     *
     * @return {@link Integer}
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * 设置错误代码
     *
     * @param errorCode 错误代码
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 得到错误味精
     *
     * @return {@link String}
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置错误味精
     *
     * @param errorMsg 错误味精
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 获取数据
     *
     * @return {@link T}
     */
    public T getData() {
        return data;
    }

    /**
     * 集数据
     *
     * @param data 数据
     */
    public void setData(T data) {
        this.data = data;
    }
}
