package com.zy_admin.community.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 小区 (ZyCommunityDto)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@SuppressWarnings("serial")
@Data
public class ZyCommunity extends Model<ZyCommunity> {
    //小区id
    @TableId
    private String communityId;
    //小区名称
    private String communityName;
    //小区编码
    private String communityCode;
    //省区划码
    private String communityProvenceCode;
    //市区划码
    private String communityCityCode;
    //区县区划码
    private String communityTownCode;
    //详细地址
    private String communityDetailedAddress;
    //经度
    private String communityLongitude;
    //纬度
    private String communityLatitude;
    //物业id
    private Long deptId;
    //排序
    private Integer communitySort;
    //创建者
    private String createBy;
    //创建时间
    private String createTime;
    //更新者
    private String updateBy;
    //更新时间
    private String updateTime;
    //备注
    private String remark;


    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getCommunityProvenceCode() {
        return communityProvenceCode;
    }

    public void setCommunityProvenceCode(String communityProvenceCode) {
        this.communityProvenceCode = communityProvenceCode;
    }

    public String getCommunityCityCode() {
        return communityCityCode;
    }

    public void setCommunityCityCode(String communityCityCode) {
        this.communityCityCode = communityCityCode;
    }

    public String getCommunityTownCode() {
        return communityTownCode;
    }

    public void setCommunityTownCode(String communityTownCode) {
        this.communityTownCode = communityTownCode;
    }

    public String getCommunityDetailedAddress() {
        return communityDetailedAddress;
    }

    public void setCommunityDetailedAddress(String communityDetailedAddress) {
        this.communityDetailedAddress = communityDetailedAddress;
    }

    public String getCommunityLongitude() {
        return communityLongitude;
    }

    public void setCommunityLongitude(String communityLongitude) {
        this.communityLongitude = communityLongitude;
    }

    public String getCommunityLatitude() {
        return communityLatitude;
    }

    public void setCommunityLatitude(String communityLatitude) {
        this.communityLatitude = communityLatitude;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Integer getCommunitySort() {
        return communitySort;
    }

    public void setCommunitySort(Integer communitySort) {
        this.communitySort = communitySort;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
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
        return this.communityId;
    }
}

