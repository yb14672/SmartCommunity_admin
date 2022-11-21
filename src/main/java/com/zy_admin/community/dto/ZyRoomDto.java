package com.zy_admin.community.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.community.entity.ZyRoom;
import com.zy_admin.community.entity.ZyUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 房屋连表dto类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyRoomDto {
    /**
     * 获取房屋
     */
    @ExcelIgnore
    private ZyRoom zyRoom;
    /**
     * 获取小区
     */
    @ExcelIgnore
    private ZyCommunity zyCommunity;
    /**
     * 获取单元
     */
    @ExcelIgnore
    private ZyUnit zyUnit;
    /**
     * 获取楼层
     */
    @ExcelIgnore
    private ZyBuilding zyBuilding;


    /**
     * 房间id
     */
    @ExcelProperty("房间id")
    @TableId
    private String roomId;
    /**
     * 小区名称
     */
    @ExcelProperty("小区名称")
    private String communityName;
    /**
     * 楼栋名称
     */
    @ExcelProperty("楼栋名称")
    private String buildingName;
    /**
     * 单元名称
     */
    @ExcelProperty("单元名称")
    private String unitName;
    /**
     * 楼层
     */
    @ExcelProperty("楼层")
    private Integer roomLevel;
    /**
     * 房间名称
     */
    @ExcelProperty("房间名称")
    private String roomName;
    /**
     * 房间编号
     */
    @ExcelProperty("房间编号")
    private String roomCode;
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
     * 小区id
     */
    @ExcelProperty("小区id")
    private String communityId;
    /**
     * 楼栋id
     */
    @ExcelProperty("楼栋id")
    private String buildingId;
    /**
     * 单元id
     */
    @ExcelProperty("单元id")
    private String unitId;
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
    private String createTime;
    /**
     * 更新者
     */
    @ExcelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ExcelProperty("更新时间")
    private String updateTime;
    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String remark;
}
