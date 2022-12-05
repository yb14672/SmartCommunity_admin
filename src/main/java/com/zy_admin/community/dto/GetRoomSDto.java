package com.zy_admin.community.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取不同状态下的房间总数
 *
 * @author lvwei
 */
@ApiModel(description = "获取不同状态下的房间总数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRoomSDto {
    /**
     * 总房间
     */
    @ApiModelProperty("总房间")
    private Integer RoomTotal;
    /**
     * 未出售的房间
     */
    @ApiModelProperty("未出售的房间")
    private Integer UnsoldTotal;
    /**
     * 已出售的房间
     */
    @ApiModelProperty("已出售的房间")
    private Integer SoldTotal;
    /**
     * 已入住的房间
     */
    @ApiModelProperty("已入住的房间")
    private Integer MoveInTotal;

}
