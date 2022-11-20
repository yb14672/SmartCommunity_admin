package com.zy_admin.community.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

/**
 * 房间 (ZyRoom)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@SuppressWarnings("serial")
public class ZyRoom extends Model<ZyRoom> {
    /**
     * 房间id
     */
    @ExcelProperty("房间id")
    private Long roomId;
    /**
     * 小区id
     */
    @ExcelProperty("小区id")
    private Long communityId;
    /**
     * 楼栋id
     */
    @ExcelProperty("楼栋id")
    private Long buildingId;
    /**
     * 单元id
     */
    @ExcelProperty("单元id")
    private Long unitId;
    /**
     * 楼层
     */
    @ExcelProperty("楼层")
    private Integer roomLevel;
    /**
     * 房间编号
     */
    @ExcelProperty("房间编号")
    private String roomCode;
    /**
     * 房间名称
     */
    @ExcelProperty("房间名称")
    private String roomName;
    /**
     * 房屋建筑面积
     */
    @ExcelProperty("房屋建筑面积")
    private Double roomAcreage;
    /**
     * 算费系数
     */
    @ExcelProperty("算费系数")
    private Double roomCost;
    /**
     * 房屋状态
     */
    @ExcelProperty("房屋状态")
    private String roomStatus;
    /**
     * 是否商铺
     */
    @ExcelProperty("是否商铺")
    private String roomIsShop;
    /**
     * 是否商品房
     */
    @ExcelProperty("是否商品房")
    private String roomSCommercialHouse;
    /**
     * 房屋户型
     */
    @ExcelProperty("房屋户型")
    private String roomHouseType;
    /**
     * 创建者
     */
    @ExcelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    private Date createTime;
    /**
     * 更新者
     */
    @ExcelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ExcelProperty("更新时间")
    private Date updateTime;
    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String remark;


    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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

    public Integer getRoomLevel() {
        return roomLevel;
    }

    public void setRoomLevel(Integer roomLevel) {
        this.roomLevel = roomLevel;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Double getRoomAcreage() {
        return roomAcreage;
    }

    public void setRoomAcreage(Double roomAcreage) {
        this.roomAcreage = roomAcreage;
    }

    public Double getRoomCost() {
        return roomCost;
    }

    public void setRoomCost(Double roomCost) {
        this.roomCost = roomCost;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomIsShop() {
        return roomIsShop;
    }

    public void setRoomIsShop(String roomIsShop) {
        this.roomIsShop = roomIsShop;
    }

    public String getRoomSCommercialHouse() {
        return roomSCommercialHouse;
    }

    public void setRoomSCommercialHouse(String roomSCommercialHouse) {
        this.roomSCommercialHouse = roomSCommercialHouse;
    }

    public String getRoomHouseType() {
        return roomHouseType;
    }

    public void setRoomHouseType(String roomHouseType) {
        this.roomHouseType = roomHouseType;
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

