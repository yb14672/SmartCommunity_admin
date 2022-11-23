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
 * 定时任务调度表(SysJob)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@ApiModel(description = "定时任务调度表(SysJob)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysJob extends Model<SysJob> {
    /**
     * 任务ID
     */
    @ApiModelProperty("任务ID")
    private Long jobId;
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
     * cron执行表达式
     */
    @ApiModelProperty("cron执行表达式")
    private String cronExpression;
    /**
     * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
    @ApiModelProperty("计划执行错误策略（1立即执行 2执行一次 3放弃执行）")
    private String misfirePolicy;
    /**
     * 是否并发执行（0允许 1禁止）
     */
    @ApiModelProperty("是否并发执行（0允许 1禁止）")
    private String concurrent;
    /**
     * 状态（0正常 1暂停）
     */
    @ApiModelProperty("状态（0正常 1暂停）")
    private String status;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTime;
    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private String updateTime;
    /**
     * 备注信息
     */
    @ApiModelProperty("备注信息")
    private String remark;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.jobId;
    }
}

