package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 定时任务调度日志表(SysJobLog)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@ApiModel(description = "定时任务调度日志表(SysJobLog)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysJobLog extends Model<SysJobLog> {
    /**
     * 任务日志ID
     */
    @ApiModelProperty("任务日志ID")
    private Long jobLogId;
    /**
     * 任务名称
     */
    @ApiModelProperty("任务名称")
    private String jobName;
    /**
     * 任务组名
     */
    @ApiModelProperty("任务组名")
    private String jobGroup;
    /**
     * 调用目标字符串
     */
    @ApiModelProperty("调用目标字符串")
    private String invokeTarget;
    /**
     * 日志信息
     */
    @ApiModelProperty("日志信息")
    private String jobMessage;
    /**
     * 执行状态（0正常 1失败）
     */
    @ApiModelProperty("执行状态（0正常 1失败）")
    private String status;
    /**
     * 异常信息
     */
    @ApiModelProperty("异常信息")
    private String exceptionInfo;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTime;
    /**
     * 获取主键值
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.jobLogId;
    }
}

