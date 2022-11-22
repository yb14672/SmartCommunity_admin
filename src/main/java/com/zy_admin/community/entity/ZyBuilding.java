package com.zy_admin.community.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 楼栋 (ZyBuilding)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyBuilding extends Model<ZyBuilding> {
    /**
     * 楼栋id
     */
    @ExcelProperty("楼栋id")
    @TableId
    private String buildingId;
    /**
     * 楼栋名称
     */
    @ExcelProperty("楼栋名称")
    private String buildingName;
    /**
     * 楼栋编码
     */
    @ExcelProperty("楼栋编码")
    private String buildingCode;
    /**
     * 楼栋面积
     */
    @ExcelProperty("楼栋面积")
    private Double buildingAcreage;
    /**
     * 小区id
     */
    @ExcelProperty("小区id")
    private String communityId;
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
    @ExcelProperty(" ")
    private String updateTime;
    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String remark;

}

