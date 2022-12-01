package com.zy_admin.community.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 房屋绑定记录表 (ZyOwnerParkRecord)实体类
 *
 * @author makejava
 * @since 2022-12-01 16:42:59
 */
public class ZyOwnerParkRecord implements Serializable {
    private static final long serialVersionUID = -19827274367849050L;
    /**
     * 车位绑定记录id
     */
    private Long recordId;
    /**
     * 车位id
     */
    private String ownerParkId;
    /**
     * 小区id
     */
    private Long communityId;
    /**
     * 业主id
     */
    private Long ownerId;
    /**
     * 绑定状态（0审核中 1绑定 2审核失败,3解绑）
     */
    private String parkBundingStatus;
    /**
     * 审核意见
     */
    private String recordAuditOpinion;
    /**
     * 审核人类型
     */
    private String recordAuditType;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;


    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getOwnerParkId() {
        return ownerParkId;
    }

    public void setOwnerParkId(String ownerParkId) {
        this.ownerParkId = ownerParkId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getParkBundingStatus() {
        return parkBundingStatus;
    }

    public void setParkBundingStatus(String parkBundingStatus) {
        this.parkBundingStatus = parkBundingStatus;
    }

    public String getRecordAuditOpinion() {
        return recordAuditOpinion;
    }

    public void setRecordAuditOpinion(String recordAuditOpinion) {
        this.recordAuditOpinion = recordAuditOpinion;
    }

    public String getRecordAuditType() {
        return recordAuditType;
    }

    public void setRecordAuditType(String recordAuditType) {
        this.recordAuditType = recordAuditType;
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

}

