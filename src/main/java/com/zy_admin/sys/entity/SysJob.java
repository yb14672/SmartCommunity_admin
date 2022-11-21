package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 定时任务调度表(SysJob)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysJob extends Model<SysJob> {
    //任务ID
    private Long jobId;
    //任务名称
    private String jobName;
    //任务组名
    private String jobGroup;
    //调用目标字符串
    private String invokeTarget;
    //cron执行表达式
    private String cronExpression;
    //计划执行错误策略（1立即执行 2执行一次 3放弃执行）
    private String misfirePolicy;
    //是否并发执行（0允许 1禁止）
    private String concurrent;
    //状态（0正常 1暂停）
    private String status;
    //创建者
    private String createBy;
    //创建时间
    private String createTime;
    //更新者
    private String updateBy;
    //更新时间
    private String updateTime;
    //备注信息
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

