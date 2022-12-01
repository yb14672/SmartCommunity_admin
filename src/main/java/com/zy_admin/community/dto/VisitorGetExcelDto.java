package com.zy_admin.community.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访客Excel模板
 * @author yb14672
 */
@ApiModel(description = "访客Excel模板")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorGetExcelDto {
    /**
     * 小区名称
     */
    @ApiModelProperty("小区名称")
    @ExcelProperty("小区名")
    private String communityName;
    /**
     * 访客姓名
     */
    @ApiModelProperty("访客姓名")
    @ExcelProperty("访客姓名")
    private String visitorName;
    /**
     * 访客手机号
     */
    @ApiModelProperty("访客手机号")
    @ExcelProperty("访客手机号")
    private String visitorPhoneNumber;
    /**
     * 到访时间
     */
    @ApiModelProperty("到访时间")
    @ExcelProperty("到访时间")
    private String visitorDate;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @ExcelProperty("创建时间")
    private String createTime;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @ExcelProperty("备注")
    private String remark;
    /**
     * 访客操作
     */
    @ApiModelProperty("访客操作")
    @ExcelProperty("是否允许进入小区")
    private String status;
}
