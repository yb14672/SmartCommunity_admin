package com.zy_admin.community.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 房屋绑定表 (ZyOwnerPark)实体类
 *
 * @author makejava
 * @since 2022-12-01 15:18:36
 */
public class ZyOwnerPark implements Serializable {
    private static final long serialVersionUID = -93830376049597730L;
    /**
     * 房屋绑定id
     */
    private Long ownerParkId;
    /**
     * 车位id
     */
    private Long parkId;
    /**
     * 业主id
     */
    private Long ownerId;
    /**
     * 绑定状态（0审核中 1绑定 2审核失败）
     */
    private String parkStatus;
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


    public Long getOwnerParkId() {
        return ownerParkId;
    }

    public void setOwnerParkId(Long ownerParkId) {
        this.ownerParkId = ownerParkId;
    }

    public Long getParkId() {
        return parkId;
    }

    public void setParkId(Long parkId) {
        this.parkId = parkId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getParkStatus() {
        return parkStatus;
    }

    public void setParkStatus(String parkStatus) {
        this.parkStatus = parkStatus;
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

