package com.zy_admin.community.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (ZyPark)实体类
 *
 * @author makejava
 * @since 2022-12-01 15:10:08
 */
@ApiModel(description = "(ZyPark)实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyPark implements Serializable {
    @ApiModelProperty(hidden = true)
    @ExcelIgnore
    private static final long serialVersionUID = 952547879564769031L;
    /**
     * 车位id
     */
    @ApiModelProperty("车位id")
    private String parkId;
    /**
     * 车位编号
     */
    @ExcelProperty("车位编号")
    @ApiModelProperty("车位编号")
    private String parkCode;
    /**
     * 删除状态
     */
    @ExcelProperty("删除状态")
    @ApiModelProperty("删除状态")
    private String delFlag;
    /**
     * 小区id
     */
    @ExcelIgnore
    @ApiModelProperty("小区id")
    private String communityId;
    /**
     * 车位类型（地下停车位/地面停车位/人防车位）
     */
    @ExcelProperty("车位类型")
    @ApiModelProperty("车位类型（地下停车位/地面停车位/人防车位）")
    private String parkType;
    /**
     * 车位状态（1停用/0启用）
     */
    @ExcelProperty("车位状态（1停用/0启用）")
    @ApiModelProperty("车位状态（1停用/0启用）")
    private String parkStatus;
    /**
     * 是否公共停车位（1私有/0公有）
     */
    @ExcelProperty("是否公共停车位（1私有/0公有）")
    @ApiModelProperty("是否公共停车位（1私有/0公有）")
    private String parkIsPublic;
    /**
     * 创建者
     */
    @ExcelProperty("创建者")
    @ApiModelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    @ApiModelProperty("创建时间")
    private String createTime;
    /**
     * 更新者
     */
    @ExcelProperty("更新者")
    @ApiModelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ExcelProperty("更新时间")
    @ApiModelProperty("更新时间")
    private String updateTime;
    /**
     * 备注
     */
    @ExcelProperty("备注")
    @ApiModelProperty("备注")
    private String remark;

}

