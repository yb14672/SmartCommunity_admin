package com.zy_admin.community.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUnitExcelDto  {
/**
 *
 */
 @ExcelProperty("社区名字")
   private String communityName;
 /**
  *
  */
    @ExcelProperty("社区楼栋")
   private String buildingName;
 /**
  *
  */
    @ExcelProperty("单元名称")
    private String unitName;
 /**
  *
  */
    @ExcelProperty("单元编号")
    private String unitCode;
 /**
  *
  */
    @ExcelProperty("层数")
    private Integer unitLevel;
 /**
  *
  */
    @ExcelProperty("建筑面积")
    private Double unitAcreage;
 /**
  *
  */
    @ExcelProperty("是否有电梯")
    private String unitHaveElevator;
 /**
  *
  */
    @ExcelProperty("創建者")
    private String createBy;
 /**
  *
  */
    @ExcelProperty("创建时间")
 private String createTime;
 /**
  *
  */
    @ExcelProperty("更新者")
    private String updateBy;
 /**
  *
  */
    @ExcelProperty("更新时间")
    private String updateTime;
 /**
  *
  */

    @ExcelProperty("备注")
    private String remark;
}
