package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

/**
 * 字典类型表(SysDictType)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:35
 */
@SuppressWarnings("serial")
public class SysDictType extends Model<SysDictType> {
    //字典主键
    private Long dictId;
    //字典名称
    private String dictName;
    //字典类型
    private String dictType;
    //状态（0正常 1停用）
    private String status;
    //创建者
    private String createBy;
    //创建时间
    private String createTime;
    //更新者
    private String updateBy;
    //更新时间
    private Date updateTime;
    //备注
    private String remark;

    public SysDictType(Long dictId, String dictName, String dictType, String status, String createBy, String createTime, String updateBy, Date updateTime, String remark) {
        this.dictId = dictId;
        this.dictName = dictName;
        this.dictType = dictType;
        this.status = status;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.remark = remark;
    }

    public SysDictType() {
    }

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SysDictType{" +
                "dictId=" + dictId +
                ", dictName='" + dictName + '\'' +
                ", dictType='" + dictType + '\'' +
                ", status='" + status + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}

