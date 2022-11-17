package com.zy_admin.community.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 单元 (ZyUnit)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@SuppressWarnings("serial")
public class ZyUnit extends Model<ZyUnit> {
    //单元id
    private String unitId;
    //小区id
    private String communityId;
    //楼栋id
    private String buildingId;
    //单元名称
    private String unitName;
    //单元编号
    private String unitCode;
    //层数
    private Integer unitLevel;
    //建筑面积
    private Double unitAcreage;
    //是否有电梯
    private String unitHaveElevator;
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

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Integer getUnitLevel() {
        return unitLevel;
    }

    public void setUnitLevel(Integer unitLevel) {
        this.unitLevel = unitLevel;
    }

    public Double getUnitAcreage() {
        return unitAcreage;
    }

    public void setUnitAcreage(Double unitAcreage) {
        this.unitAcreage = unitAcreage;
    }

    public String getUnitHaveElevator() {
        return unitHaveElevator;
    }

    public void setUnitHaveElevator(String unitHaveElevator) {
        this.unitHaveElevator = unitHaveElevator;
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

