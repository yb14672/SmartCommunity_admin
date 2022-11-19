package com.zy_admin.community.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 房间 (ZyRoom)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyRoom extends Model<ZyRoom> {
    //房间id
    private Long roomId;
    //小区id
    private Long communityId;
    //楼栋id
    private Long buildingId;
    //单元id
    private Long unitId;
    //楼层
    private Integer roomLevel;
    //房间编号
    private String roomCode;
    //房间名称
    private String roomName;
    //房屋建筑面积
    private Double roomAcreage;
    //算费系数
    private Double roomCost;
    //房屋状态
    private String roomStatus;
    //是否商铺
    private String roomIsShop;
    //是否商品房
    private String roomSCommercialHouse;
    //房屋户型
    private String roomHouseType;
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
}

