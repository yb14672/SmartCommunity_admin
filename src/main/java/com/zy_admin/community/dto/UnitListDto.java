package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyUnit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 14208
 */
@ApiModel(description = "单元信息+地址")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitListDto extends ZyUnit {
    /**
     * 小区名
     */
    @ApiModelProperty("小区名")
    private String communityName;
    /**
     * 楼栋名
     */
    @ApiModelProperty("楼栋名")
    private String buildingName;

}
