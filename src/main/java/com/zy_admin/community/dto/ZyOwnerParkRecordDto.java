package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyOwnerParkRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "房间与业主关联信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyOwnerParkRecordDto extends ZyOwnerParkRecord {
    /**
     * 车位编号
     */
    @ApiModelProperty("车位编号")
    private String parkCode;
    /**
     * 小区名称
     */
    @ApiModelProperty("小区名称")
    private String communityName;
    /**
     * 车位类型
     */
    @ApiModelProperty("车位类型")
    private String parkType;
    /**
     * 车位状态
     */
    @ApiModelProperty("车位状态")
    private String parkStatus;
    /**
     * 是否公共停车位
     */
    @ApiModelProperty("是否公共停车位")
    private String parkIsPublic;
    /**
     * 审核人user
     */
    @ApiModelProperty("审核人")
    private String updateBy;
    /**
     * 审核时间
     */
    @ApiModelProperty("审核时间")
    private String updateTime;
    /**
     * 绑定状态
     */
    @ApiModelProperty("绑定状态")
    private String parkBundingStatus;
    /**
     * 记录审计意见
     */
    @ApiModelProperty("记录审计意见")
    private String recordAuditOpinion;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
