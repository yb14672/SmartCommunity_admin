package com.zy_admin.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (ZyPark)实体类
 *
 * @author makejava
 * @since 2022-12-01 15:10:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyPark implements Serializable {
    private static final long serialVersionUID = 952547879564769031L;
    /**
     * 车位id
     */
    private Long parkId;
    /**
     * 车位编号
     */
    private String parkCode;
    /**
     * 小区id
     */
    private Long communityId;
    /**
     * 车位类型（地下停车位/地面停车位/人防车位）
     */
    private String parkType;
    /**
     * 算费系数
     */
    private Double parkCost;
    /**
     * 车位状态（1停用/0启用）
     */
    private String parkStatus;
    /**
     * 是否公共停车位（1私有/0公有）
     */
    private String parkIsPublic;
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


    public Long getParkId() {
        return parkId;
    }

    public void setParkId(Long parkId) {
        this.parkId = parkId;
    }

    public String getParkCode() {
        return parkCode;
    }

    public void setParkCode(String parkCode) {
        this.parkCode = parkCode;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getParkType() {
        return parkType;
    }

    public void setParkType(String parkType) {
        this.parkType = parkType;
    }

    public Double getParkCost() {
        return parkCost;
    }

    public void setParkCost(Double parkCost) {
        this.parkCost = parkCost;
    }

    public String getParkStatus() {
        return parkStatus;
    }

    public void setParkStatus(String parkStatus) {
        this.parkStatus = parkStatus;
    }

    public String getParkIsPublic() {
        return parkIsPublic;
    }

    public void setParkIsPublic(String parkIsPublic) {
        this.parkIsPublic = parkIsPublic;
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

