package com.zy_admin.community.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 房屋绑定记录表 (ZyOwnerRoomRecord)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@SuppressWarnings("serial")
public class ZyOwnerRoomRecord extends Model<ZyOwnerRoomRecord> {
    //房屋绑定记录id
    private Long recordId;
    //房屋绑定id
    private String ownerRoomId;
    //小区id
    private Long communityId;
    //楼栋id
    private Long buildingId;
    //单元id
    private Long unitId;
    //房间id
    private Long roomId;
    //业主id
    private Long ownerId;
    //业主类型
    private String ownerType;
    //绑定状态（0审核中 1绑定 2审核失败,3解绑）
    private String roomStatus;
    //审核意见
    private String recordAuditOpinion;
    //审核人类型
    private String recordAuditType;
    //创建人id
    private Long createById;
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


    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getOwnerRoomId() {
        return ownerRoomId;
    }

    public void setOwnerRoomId(String ownerRoomId) {
        this.ownerRoomId = ownerRoomId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
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

    public Long getCreateById() {
        return createById;
    }

    public void setCreateById(Long createById) {
        this.createById = createById;
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

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.recordId;
    }
}

