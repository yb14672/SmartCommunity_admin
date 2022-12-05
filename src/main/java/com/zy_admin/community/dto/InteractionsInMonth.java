package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyCommunityInteraction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 一月内的交互信息
 *
 * @author yb14672
 * Time:2022 - 2022/12/4 - 20:56
 **/
@ApiModel(description = "一月内的交互信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InteractionsInMonth extends ZyCommunityInteraction {
    /**
     * 小区名
     */
    @ApiModelProperty("小区名")
    private String communityName;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String ownerNickname;

    /**
     * 用户真实姓名
     */
    @ApiModelProperty("用户真实姓名")
    private String ownerRealName;
}
