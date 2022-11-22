package com.zy_admin.sys.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 操作日志记录(SysOperLog)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@ApiModel(description = "操作日志记录(SysOperLog)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysOperLog extends Model<SysOperLog> {
    /**
     * 日志主键
     */
    @ApiModelProperty("日志主键")
    @ExcelProperty("日志ID")
    private Long operId;
    /**
     * 模块标题
     */
    @ApiModelProperty("模块标题")
    @ExcelProperty("模块标题")
    private String title;
    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @ApiModelProperty("业务类型（0其它 1新增 2修改 3删除）")
    @ExcelProperty("业务类型(具体请看字典数据)")
    private Integer businessType;
    /**
     * 方法名称
     */
    @ApiModelProperty("方法名称")
    @ExcelProperty("方法名称")
    private String method;
    /**
     * 请求方式
     */
    @ApiModelProperty("请求方式")
    @ExcelProperty("请求方式")
    private String requestMethod;
    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    @ApiModelProperty("操作类别（0其它 1后台用户 2手机端用户）")
    @ExcelProperty("操作类别（0其它 1后台用户 2手机端用户）")
    private Integer operatorType;
    /**
     * 操作人员
     */
    @ApiModelProperty("操作人员")
    @ExcelProperty("操作人员")
    private String operName;
    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    @ExcelProperty("部门名称")
    private String deptName;
    /**
     * 请求URL
     */
    @ApiModelProperty("请求URL")
    @ExcelProperty("请求URL")
    private String operUrl;
    /**
     * 主机地址
     */
    @ApiModelProperty("主机地址")
    @ExcelProperty("主机地址")
    private String operIp;
    /**
     * 操作地点
     */
    @ApiModelProperty("操作地点")
    @ExcelProperty("操作地点")
    private String operLocation;
    /**
     * 请求参数
     */
    @ApiModelProperty("请求参数")
    @ExcelProperty("请求参数")
    private String operParam;
    /**
     * 返回参数
     */
    @ApiModelProperty("返回参数")
    @ExcelProperty("返回参数")
    private String jsonResult;
    /**
     * 操作状态（0正常 1异常）
     */
    @ApiModelProperty("操作状态（0正常 1异常）")
    @ExcelProperty("操作状态（0正常 1异常）")
    private Integer status;
    /**
     * 错误消息
     */
    @ApiModelProperty("错误消息")
    @ExcelProperty("错误消息")
    private String errorMsg;
    /**
     * 操作时间
     */
    @ApiModelProperty("操作时间")
    @ExcelProperty("操作时间")
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

