package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyBuilding;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "楼栋信息+小区名称")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZyBuildingDto extends ZyBuilding {
    /**
     * 小区名称
     */
    @ApiModelProperty("小区名称")
    private String communityName;
}
