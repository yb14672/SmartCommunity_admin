package com.zy_admin.community.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 房屋数量相关信息
 *
 * @author yb14672
 * Time:2022 - 2022/12/5 - 0:12
 **/
@ApiModel(description = "房屋数量相关信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomInfoDto {
    /**
     * 小区名
     */
    @ApiModelProperty("小区名")
    private List<String> communityName = new ArrayList<>();
    /**
     * 房屋数量
     */
    @ApiModelProperty("房屋数量")
    private List<Double> roomNum = new ArrayList<>();
    /**
     * 已绑定数量
     */
    @ApiModelProperty("已绑定数量")
    private List<Double> bingingNum = new ArrayList<>();
    /**
     * 绑定率
     */
    @ApiModelProperty("绑定率")
    private List<Double> bindingRate = new ArrayList<>();
}
