package com.zy_admin.community.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 投诉建议 (ZyComplaintSuggest)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@SuppressWarnings("serial")
public class ZyComplaintSuggest extends Model<ZyComplaintSuggest> {
    //id
    private Long complaintSuggestId;
    //小区id
    private Long communityId;
    //类型(投诉、建议)
    private String complaintSuggestType;
    //内容
    private String complaintSuggestContent;
    //创建者
    private String createBy;
    //创建时间
    private Date createTime;
    //更新者
    private String updateBy;
    //更新时间
    private Date updateTime;
    //备注
    private String remark;
    //投诉人ID
    private Long userId;


    public Long getComplaintSuggestId() {
        return complaintSuggestId;
    }

    public void setComplaintSuggestId(Long complaintSuggestId) {
        this.complaintSuggestId = complaintSuggestId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getComplaintSuggestType() {
        return complaintSuggestType;
    }

    public void setComplaintSuggestType(String complaintSuggestType) {
        this.complaintSuggestType = complaintSuggestType;
    }

    public String getComplaintSuggestContent() {
        return complaintSuggestContent;
    }

    public void setComplaintSuggestContent(String complaintSuggestContent) {
        this.complaintSuggestContent = complaintSuggestContent;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.complaintSuggestId;
    }
}

