package com.zy_admin.community.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 房屋数量相关信息
 *
 * @author yb14672
 * Time:2022 - 2022/12/4 - 23:43
 */
@ApiModel(description = "房屋数量相关信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomInfo {
    /**
     * 小区名
     */
    @ApiModelProperty("小区名")
    private String communityName;
    /**
     * 房屋数量
     */
    @ApiModelProperty("房屋数量")
    private Double roomNum;
    /**
     * 已绑定数量
     */
    @ApiModelProperty("已绑定数量")
    private Double bingingNum;
    /**
     * 绑定率
     */
    @ApiModelProperty("绑定率")
    private Double bindingRate;
}
