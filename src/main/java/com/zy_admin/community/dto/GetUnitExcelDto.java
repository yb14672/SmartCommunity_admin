package com.zy_admin.community.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUnitExcelDto  {

    @ExcelProperty("社区名字")
   private String communityName;
    @ExcelProperty("社区楼栋")
   private String buildingName;
    @ExcelProperty("单元名称")
    private String unitName;
    //单元编号
    @ExcelProperty("单元编号")
    private String unitCode;
    //层数
    @ExcelProperty("层数")
    private Integer unitLevel;
    //建筑面积
    @ExcelProperty("建筑面积")
    private Double unitAcreage;
    //是否有电梯
    @ExcelProperty("是否有电梯")
    private String unitHaveElevator;
    @ExcelProperty("創建者")
    private String createBy;
    //创建时间
    @ExcelProperty("创建时间")
    private String createTime;
    //更新者
    @ExcelProperty("更新者")
    private String updateBy;
    //更新时间
    @ExcelProperty("更新时间")
    private String updateTime;
    //备注
    @ExcelProperty("备注")
    private String remark;
}
