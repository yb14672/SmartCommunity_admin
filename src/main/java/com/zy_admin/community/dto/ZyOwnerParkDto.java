package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyOwnerPark;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyOwnerParkDto extends ZyOwnerPark {

    /**
     * 小区名称
     */
    @ApiModelProperty("小区名称")
    private String communityName;

    /**
     * 车位编号
     */
    @ApiModelProperty("车位编号")
    private String parkCode;

    /**
     * 车位类型
     */
    @ApiModelProperty("车位类型")
    private String parkType;

    /**
     * 是否公共停车位
     */
    @ApiModelProperty("是否公共停车位")
    private String parkIsPublic;

    /**
     * 车位状态
     */
    @ApiModelProperty("车位状态")
    private String parkStatus;


}
