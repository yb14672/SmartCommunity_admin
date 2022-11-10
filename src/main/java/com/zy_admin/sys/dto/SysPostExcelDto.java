package com.zy_admin.sys.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jason.ZHANG
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysPostExcelDto  extends Model<SysPostExcelDto> {

    //岗位ID
    @ExcelProperty("岗位ID")
    private Long postId;
    //岗位编码
    @ExcelProperty("岗位编码")
    private String postCode;
    //岗位名称
    @ExcelProperty("岗位名称")
    private String postName;
    //显示顺序
    @ExcelProperty("显示顺序")
    private Integer postSort;
    //状态（0正常 1停用）
    @ExcelProperty("状态（0正常 1停用）")
    private String status;
    //创建者
    @ExcelProperty("创建者")
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