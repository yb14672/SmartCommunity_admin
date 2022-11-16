package com.zy_admin.sys.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 定时任务调度日志表(SysJobLog)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@SuppressWarnings("serial")
public class SysJobLog extends Model<SysJobLog> {
    //任务日志ID
    private Long jobLogId;
    //任务名称
    private String jobName;
    //任务组名
    private String jobGroup;
    //调用目标字符串
    private String invokeTarget;
    //日志信息
    private String jobMessage;
    //执行状态（0正常 1失败）
    private String status;
    //异常信息
    private String exceptionInfo;
    //创建时间
    private Date createTime;


    public Long getJobLogId() {
        return jobLogId;
    }

    public void setJobLogId(Long jobLogId) {
        this.jobLogId = jobLogId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getInvokeTarget() {
        return invokeTarget;
    }

    public void setInvokeTarget(String invokeTarget) {
        this.invokeTarget = invokeTarget;
    }

    public String getJobMessage() {
        return jobMessage;
    }

    public void setJobMessage(String jobMessage) {
        this.jobMessage = jobMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.jobLogId;
    }
}

