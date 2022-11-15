package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 操作日志记录(SysOperLog)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysOperLog extends Model<SysOperLog> {
    @ApiModelProperty("日志主键")
    private Long operId;
    @ApiModelProperty("模块标题")
    private String title;
    @ApiModelProperty("业务类型(0其它 1新增 2修改 3删除)")
    private Integer businessType;
    @ApiModelProperty("方法名称")
    private String method;
    @ApiModelProperty("请求方式")
    private String requestMethod;
    @ApiModelProperty("操作类别（0其它 1后台用户 2手机端用户）")
    private Integer operatorType;
    @ApiModelProperty("操作人员")
    private String operName;
    @ApiModelProperty("部门名称")
    private String deptName;
    @ApiModelProperty("请求URL")
    private String operUrl;
    @ApiModelProperty("主机地址")
    private String operIp;
    @ApiModelProperty("操作地点")
    private String operLocation;
    // 请求参数
    private String operParam;
    @ApiModelProperty("返回参数")
    private String jsonResult;
    @ApiModelProperty("操作状态（0正常 1异常）")
    private Integer status;
    @ApiModelProperty("错误消息")
    private String errorMsg;
    @ApiModelProperty("操作时间")
    private String operTime;


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

