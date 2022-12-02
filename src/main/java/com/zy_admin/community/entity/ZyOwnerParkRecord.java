package com.zy_admin.community.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 房屋绑定记录表 (ZyOwnerParkRecord)实体类
 *
 * @author makejava
 * @since 2022-12-01 15:10:08
 */
@ApiModel(description = "房屋绑定记录表 (ZyOwnerParkRecord)实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyOwnerParkRecord implements Serializable {
    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = -30157378326104315L;
    /**
     * 车位绑定记录id
     */
    @ApiModelProperty("车位绑定记录id")
    private String recordId;
    /**
     * 车位id
     */
    @ApiModelProperty("车位id")
    private String ownerParkId;
    /**
     * 小区id
     */
    @ApiModelProperty("小区id")
    private String communityId;
    /**
     * 业主id
     */
    @ApiModelProperty("业主id")
    private String ownerId;
    /**
     * 绑定状态（0审核中 1绑定 2审核失败,3解绑）
     */
    @ApiModelProperty("绑定状态（0审核中 1绑定 2审核失败,3解绑）")
    private String parkBundingStatus;
    /**
     * 审核意见
     */
    @ApiModelProperty("审核意见")
    private String recordAuditOpinion;
    /**
     * 审核人类型
     */
    @ApiModelProperty("审核人类型")
    private String recordAuditType;
    /**
     * 车牌号
     */
    private String carNumber;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTime;
    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private String updateTime;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}

