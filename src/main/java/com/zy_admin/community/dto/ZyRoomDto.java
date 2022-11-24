package com.zy_admin.community.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zy_admin.community.entity.ZyBuilding;
import com.zy_admin.community.entity.ZyCommunity;
import com.zy_admin.community.entity.ZyRoom;
import com.zy_admin.community.entity.ZyUnit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 房屋连表dto类
 *
 * @author 吕蔚霖
 * @date 2022/11/22
 */
@ApiModel(description = "房间dto 房屋连表dto类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyRoomDto {
    /**
     * 房间
     */
    @ApiModelProperty("房间")
    @ExcelIgnore
    private ZyRoom zyRoom;
    /**
     * 社区
     */
    @ApiModelProperty("社区")
    @ExcelIgnore
    private ZyCommunity zyCommunity;
    /**
     * 单位
     */
    @ApiModelProperty("单位")
    @ExcelIgnore
    private ZyUnit zyUnit;
    /**
     * 建筑
     */
    @ApiModelProperty("建筑")
    @ExcelIgnore
    private ZyBuilding zyBuilding;


    /**
     * 房间id
     */
    @ApiModelProperty("房间id")
    @ExcelProperty("房间id")
    @TableId
    private String roomId;
    /**
     * 社区名字
     */
    @ApiModelProperty("社区名字")
    @ExcelProperty("小区名称")
    private String communityName;
    /**
     * 建筑名字
     */
    @ApiModelProperty("建筑名字")
    @ExcelProperty("楼栋名称")
    private String buildingName;
    /**
     * 单位名字
     */
    @ApiModelProperty("单位名字")
    @ExcelProperty("单元名称")
    private String unitName;
    /**
     * 房间水平
     */
    @ApiModelProperty("房间水平")
    @ExcelProperty("楼层")
    private Integer roomLevel;
    /**
     * 房间名字
     */
    @ApiModelProperty("房间名字")
    @ExcelProperty("房间名称")
    private String roomName;
    /**
     * 房间代码
     */
    @ApiModelProperty("房间代码")
    @ExcelProperty("房间编号")
    private String roomCode;
    /**
     * 房间面积
     */
    @ApiModelProperty("房间面积")
    @ExcelProperty("房屋建筑面积")
    private Double roomAcreage;
    /**
     * 房间费用
     */
    @ApiModelProperty("房间费用")
    @ExcelProperty("算费系数")
    private Double roomCost;


    /**
     * 社区id
     */
    @ApiModelProperty("社区id")
    @ExcelProperty("小区id")
    private String communityId;
    /**
     * 构建id
     */
    @ApiModelProperty("构建id")
    @ExcelProperty("楼栋id")
    private String buildingId;
    /**
     * 单位标识
     */
    @ApiModelProperty("单位标识")
    @ExcelProperty("单元id")
    private String unitId;
    /**
     * 房间状态
     */
    @ApiModelProperty("房间状态")
    @ExcelProperty("房屋状态")
    private String roomStatus;
    /**
     * 房间是商店
     */
    @ApiModelProperty("房间是商店")
    @ExcelProperty("是否商铺")
    private String roomIsShop;
    /**
     * 房间scommercial房子
     */
    @ApiModelProperty("房间scommercial房子")
    @ExcelProperty("是否商品房")
    private String roomSCommercialHouse;
    /**
     * 房子房间类型
     */
    @ApiModelProperty("房子房间类型")
    @ExcelProperty("房屋户型")
    private String roomHouseType;
    /**
     * 创建
     */
    @ApiModelProperty("创建")
    @ExcelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @ExcelProperty("创建时间")
    private String createTime;
    /**
     * 更新
     */
    @ApiModelProperty("更新")
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
