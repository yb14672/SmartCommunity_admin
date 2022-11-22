package com.zy_admin.community.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 得到单位excel dto
 *
 * @author 26934
 * @date 2022/11/22
 */
@ApiModel(value = "单元导出Excel模板",description = "单元信息和小区地址")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUnitExcelDto {
    /**
     * 社区名字
     */
    @ApiModelProperty("社区名字")
    @ExcelProperty("社区名字")
    private String communityName;
    /**
     * 楼栋名字
     */
    @ApiModelProperty("楼栋名字")
    @ExcelProperty("社区楼栋")
    private String buildingName;
    /**
     * 单位名字
     */
    @ApiModelProperty("单位名字")
    @ExcelProperty("单元名称")
    private String unitName;
    /**
     * 单位代码
     */
    @ApiModelProperty("单位代码")
    @ExcelProperty("单元编号")
    private String unitCode;
    /**
     * 单位级
     */
    @ApiModelProperty("单位级")
    @ExcelProperty("层数")
    private Integer unitLevel;
    /**
     * 单位面积
     */
    @ApiModelProperty("单位面积")
    @ExcelProperty("建筑面积")
    private Double unitAcreage;
    /**
     * 单位有电梯
     */
    @ApiModelProperty("单位有电梯")
    @ExcelProperty("是否有电梯")
    private String unitHaveElevator;
    /**
     * 创建
     */
    @ApiModelProperty("创建")
    @ExcelProperty("創建者")
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
