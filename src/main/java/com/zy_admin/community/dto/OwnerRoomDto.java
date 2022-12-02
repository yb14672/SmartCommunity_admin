package com.zy_admin.community.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业主详情地址
 * @author fangqian
 */
@ApiModel(description = "业主详情地址")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerRoomDto {
    /**
     * 小区Id
     */
    @ApiModelProperty("小区Id")
    private String communityId;
    /**
     * 小区名称
     */
    @ApiModelProperty("小区名称")
    private String communityName;
    /**
     * 楼栋id
     */
    @ApiModelProperty("楼栋id")
    private String buildingId;
    /**
     * 楼栋名称
     */
    @ApiModelProperty("楼栋名称")
    private String buildingName;
    /**
     * 单元Id
     */
    @ApiModelProperty("单元Id")
    private String unitId;
    /**
     * 单元名称
     */
    @ApiModelProperty("单元名称")
    private String unitName;
    /**
     * 房间id
     */
    @ApiModelProperty("房间id")
    private String roomId;
    /**
     * 房间号
     */
    @ApiModelProperty("房间号")
    private String roomName;
    /**
     * 审核状态
     */
    @ApiModelProperty("审核状态")
    private String roomStatus;
    /**
     * 绑定时间
     */
    @ApiModelProperty("绑定时间")
    private String createTime;
}
