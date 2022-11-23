package com.zy_admin.community.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 房间 (ZyRoom)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@ApiModel(description = "房间 (ZyRoom)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyRoom extends Model<ZyRoom> {
    /**
     * 房间id
     */
    @ApiModelProperty("房间id")
    @ExcelProperty("房间id")
    @TableId
    private String roomId;
    /**
     * 小区id
     */
    @ApiModelProperty("小区id")
    @ExcelProperty("小区id")
    private String communityId;
    /**
     * 楼栋id
     */
    @ApiModelProperty("楼栋id")
    @ExcelProperty("楼栋id")
    private String buildingId;
    /**
     * 单元id
     */
    @ApiModelProperty("单元id")
    @ExcelProperty("单元id")
    private String unitId;
    /**
     * 楼层
     */
    @ApiModelProperty("楼层")
    @ExcelProperty("楼层")
    private Integer roomLevel;
    /**
     * 房间编号
     */
    @ApiModelProperty("房间编号")
    @ExcelProperty("房间编号")
    private String roomCode;
    /**
     * 房间名称
     */
    @ApiModelProperty("房间名称")
    @ExcelProperty("房间名称")
    private String roomName;
    /**
     * 房屋建筑面积
     */
    @ApiModelProperty("房屋建筑面积")
    @ExcelProperty("房屋建筑面积")
    private Double roomAcreage;
    /**
     * 算费系数
     */
    @ApiModelProperty("算费系数")
    @ExcelProperty("算费系数")
    private Double roomCost;
    /**
     * 房屋状态
     */
    @ApiModelProperty("房屋状态")
    @ExcelProperty("房屋状态")
    private String roomStatus;
    /**
     * 是否商铺
     */
    @ApiModelProperty("是否商铺")
    @ExcelProperty("是否商铺")
    private String roomIsShop;
    /**
     * 是否商品房
     */
    @ApiModelProperty("是否商品房")
    @ExcelProperty("是否商品房")
    private String roomSCommercialHouse;
    /**
     * 房屋户型
     */
    @ApiModelProperty("房屋户型")
    @ExcelProperty("房屋户型")
    private String roomHouseType;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    @ExcelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @ExcelProperty("创建时间")
    private String createTime;
    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    @ExcelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @ExcelProperty("更新时间")
    private String updateTime;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @ExcelProperty("备注")
    private String remark;
}

