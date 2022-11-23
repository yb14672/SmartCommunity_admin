package com.zy_admin.community.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 房间与业主关联信息
 * @author 屈羽星
 */
@ApiModel(description = "房间与业主关联信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyOwnerRoomRecordDto{
    /**
     * 业主真实姓名
     */
    @ApiModelProperty("业主真实姓名")
    private String ownerRealName;
    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private String updateBy;
    /**
     * 所有者类型
     */
    @ApiModelProperty("所有者类型")
    private String ownerType;
    /**
     * 房间状态
     */
    @ApiModelProperty("房间状态")
    private String roomStatus;
    /**
     * 记录审计意见
     */
    @ApiModelProperty("记录审计意见")
    private String recordAuditOpinion;
    /**
     * 创建id
     */
    @ApiModelProperty("创建id")
    private Long createById;
    /**
     * 记录审计类型
     */
    @ApiModelProperty("记录审计类型")
    private String recordAuditType;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
