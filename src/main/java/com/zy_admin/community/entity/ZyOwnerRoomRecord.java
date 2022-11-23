package com.zy_admin.community.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 房屋绑定记录表 (ZyOwnerRoomRecord)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@ApiModel(description = "房屋绑定记录表 (ZyOwnerRoomRecord)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyOwnerRoomRecord extends Model<ZyOwnerRoomRecord> {
    /**
     * 房屋绑定记录id
     */
    @ApiModelProperty("房屋绑定记录id")
    private String recordId;
    /**
     * 房屋绑定id
     */
    @ApiModelProperty("房屋绑定id")
    private String ownerRoomId;
    /**
     * 小区id
     */
    @ApiModelProperty("小区id")
    private String communityId;
    /**
     * 楼栋id
     */
    @ApiModelProperty("楼栋id")
    private String buildingId;
    /**
     * 单元id
     */
    @ApiModelProperty("单元id")
    private String unitId;
    /**
     * 房间id
     */
    @ApiModelProperty("房间id")
    private String roomId;
    /**
     * 业主id
     */
    @ApiModelProperty("业主id")
    private String ownerId;
    /**
     * 业主类型
     */
    @ApiModelProperty("业主类型")
    private String ownerType;
    /**
     *  绑定状态（0审核中 1绑定 2审核失败,3解绑）
     */
    @ApiModelProperty("绑定状态（0审核中 1绑定 2审核失败,3解绑）")
    private String roomStatus;
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
     * 创建人id
     */
    @ApiModelProperty("创建人id")
    private Long createById;
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

