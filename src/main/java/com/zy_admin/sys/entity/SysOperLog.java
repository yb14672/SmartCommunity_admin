package com.zy_admin.sys.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志记录(SysOperLog)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysOperLog extends Model<SysOperLog> {
    //  日志主键
    @ExcelProperty("日志ID")
    private Long operId;

    //  模块标题
    @ExcelProperty("模块标题")
    private String title;

    //  业务类型（0其它 1新增 2修改 3删除）
    @ExcelProperty("业务类型(具体请看字典数据)")
    private Integer businessType;

    //  方法名称
    @ExcelProperty("方法名称")
    private String method;

    //  请求方式
    @ExcelProperty("请求方式")
    private String requestMethod;

    //  操作类别（0其它 1后台用户 2手机端用户）
    @ExcelProperty("操作类别（0其它 1后台用户 2手机端用户）")
    private Integer operatorType;

    //  操作人员
    @ExcelProperty("操作人员")
    private String operName;

    //  部门名称
    @ExcelProperty("部门名称")
    private String deptName;

    //  请求URL
    @ExcelProperty("请求URL")
    private String operUrl;

    //  主机地址
    @ExcelProperty("主机地址")
    private String operIp;

    //  操作地点
    @ExcelProperty("操作地点")
    private String operLocation;

    //  请求参数
    @ExcelProperty("请求参数")
    private String operParam;

    //  返回参数
    @ExcelProperty("返回参数")
    private String jsonResult;

    //  操作状态（0正常 1异常）
    @ExcelProperty("操作状态（0正常 1异常）")
    private Integer status;

    //  错误消息
    @ExcelProperty("错误消息")
    private String errorMsg;

    //  操作时间
    @ExcelProperty("操作时间")
    private Date operTime;


    public Long getOperId() {
        return operId;
    }

    public void setOperId(Long operId) {
        this.operId = operId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOperUrl() {
        return operUrl;
    }

    public void setOperUrl(String operUrl) {
        this.operUrl = operUrl;
    }

    public String getOperIp() {
        return operIp;
    }

    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }

    public String getOperLocation() {
        return operLocation;
    }

    public void setOperLocation(String operLocation) {
        this.operLocation = operLocation;
    }

    public String getOperParam() {
        return operParam;
    }

    public void setOperParam(String operParam) {
        this.operParam = operParam;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.operId;
    }
}

