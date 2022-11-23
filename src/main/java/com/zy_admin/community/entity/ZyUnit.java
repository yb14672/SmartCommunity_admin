package com.zy_admin.community.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单元 (ZyUnit)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:03
 */
@ApiModel(description = "单元 (ZyUnit)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyUnit extends Model<ZyUnit> {
    /**
     * 单元id
     */
    @ApiModelProperty("单元id")
    private String unitId;
    /**
     * 小区id
     */
    @ApiModelProperty("小区id")
    private String communityId;
    /**
     * 楼栋id
     */
    @ApiModelProperty("楼栋id")
    private String buildingId;
    /**
     * 单元名称
     */
    @ApiModelProperty("单元名称")
    private String unitName;
    /**
     * 单元编号
     */
    @ApiModelProperty("单元编号")
    private String unitCode;
    /**
     * 层数
     */
    @ApiModelProperty("层数")
    private Integer unitLevel;
    /**
     * 建筑面积
     */
    @ApiModelProperty("建筑面积")
    private Double unitAcreage;
    /**
     * 是否有电梯
     */
    @ApiModelProperty("是否有电梯")
    private String unitHaveElevator;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTime;
    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private String updateTime;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}