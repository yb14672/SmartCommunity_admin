package com.zy_admin.community.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "投诉建议dto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyComplaintSuggestDto {
    /**
     * id
     */
    @ApiModelProperty("id")
    @ExcelProperty("id")
    private String complaintSuggestId;
    /**
     * 小区id
     */
    @ApiModelProperty("小区id")
    @ExcelProperty("小区id")
    private String communityId;
    /**
     * 类型(投诉、建议)
     */
    @ApiModelProperty("类型(投诉、建议)")
    @ExcelProperty("类型(投诉、建议)")
    private String complaintSuggestType;
    /**
     * 内容
     */
    @ApiModelProperty("内容")
    @ExcelProperty("内容")
    private String complaintSuggestContent;
    /**
     * 业主真实名字
     */
    @ApiModelProperty("业主真实名字")
    @ExcelProperty("业主真实名字")
    private String ownerRealName;

    /**
     * 创建者电话
     */
    @ApiModelProperty("创建者电话")
    @ExcelProperty("创建者电话")
    private String ownerPhoneNumber;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    @ExcelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @ExcelProperty("创建时间")
    private String createTime;
    /**
     *  投诉建议图片
     */
    @ApiModelProperty("投诉建议图片")
    @ExcelProperty("投诉建议图片")
    private List<String> filesUrl;

    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
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
    /**
     * 投诉人ID
     */
    @ApiModelProperty("投诉人ID")
    @ExcelProperty("投诉人ID")
    private String userId;
    /**
     * 回复
     */
    @ApiModelProperty("回复")
    @ExcelProperty("回复")
    private String reply;
}
