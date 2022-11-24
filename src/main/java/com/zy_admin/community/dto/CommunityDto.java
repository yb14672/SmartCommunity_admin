package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyCommunity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author admin
 */
@ApiModel(value = "小区详情",description = "小区信息+具体地址")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDto extends ZyCommunity {

    /**
     * 省名称
     */
    @ApiModelProperty("省名称")
    private String communityProvenceName;
    /**
     * 城市名称
     */
    @ApiModelProperty("城市名称")
    private String communityCityName;
    /**
     * 区名称
     */
    @ApiModelProperty("区名称")
    private String communityTownName;
}
