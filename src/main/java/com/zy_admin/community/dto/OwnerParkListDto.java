package com.zy_admin.community.dto;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.zy_admin.community.entity.ZyOwner;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerParkListDto extends ZyOwner {
    /**
     * 小区名字
     */
    @ExcelProperty("小区名字")
    @ApiModelProperty("小区名字")
    private String communityName;
    /**
     * 车位编号
     */
    @ExcelProperty("车位编号")
    @ApiModelProperty("车位编号")
    private String parkCode;
    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    @ApiModelProperty("创建时间")
    private String createTime;
    /**
     * 车位类型
     */
    @ExcelProperty("车位类型")
    @ApiModelProperty("车位类型")
    private String parkType;
    /**
     * 业主和车位关联id
     */
    @ExcelIgnore
    @ApiModelProperty("业主和车位关联id")
    private String ownerParkId;
    /**
     * 车位id
     */
    @ExcelIgnore
    @ApiModelProperty("车位id")
    private String parkId;
    /**
     * 小区id
     */
    @ExcelIgnore
    @ApiModelProperty("小区id")
    private String communityId;
    /**
     * 审核历史id
     */
    @ExcelIgnore
    @ApiModelProperty("审核历史id")
    private String recordId;
    /**
     * 车位绑定状态
     */
    @ExcelProperty("车位绑定状态")
    @ApiModelProperty("车位绑定状态")
    private String parkBundingStatus;
    /**
     * 审核意见
     */
    @ExcelProperty("审核意见")
    @ApiModelProperty("审核意见")
    private String recordAuditOpinion;
    /**
     * 审核人类型
     */
    @ExcelProperty("审核人类型")
    @ApiModelProperty("审核人类型")
    private String recordAuditType;

}
