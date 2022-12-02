package com.zy_admin.community.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 房屋绑定表 (ZyOwnerPark)实体类
 *
 * @author makejava
 * @since 2022-12-01 15:18:36
 */
@ApiModel(description = "房屋绑定表 (ZyOwnerPark)实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyOwnerPark implements Serializable {
    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 276187551511950633L;
    /**
     * 房屋绑定id
     */
    @ApiModelProperty("房屋绑定id")
    private String ownerParkId;
    /**
     * 车位id
     */
    @ApiModelProperty("车位id")
    private String parkId;
    /**
     * 业主id
     */
    @ApiModelProperty("业主id")
    private String ownerId;
    /**
     * 绑定状态（0审核中 1绑定 2审核失败）
     */
    @ApiModelProperty("绑定状态（0审核中 1绑定 2审核失败）")
    private String parkOwnerStatus;
    /**
     * 车牌号
     */
    @ApiModelProperty("车牌号")
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

