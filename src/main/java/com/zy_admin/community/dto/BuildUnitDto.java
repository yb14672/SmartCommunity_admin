package com.zy_admin.community.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yb14672
 * Time:2022/11/21 - 11:30
 **/
@ApiModel(value = "楼栋树",description = "楼栋与单元树状菜单")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildUnitDto {
    /**
     * 名字--用于显示
     */
    @ApiModelProperty("名字--用于显示")
    private String label;
    /**
     * ID--用于回显
     */
    @ApiModelProperty("ID--用于回显")
    private String value;

    /**
     * 对应的单元
     */
    @ApiModelProperty("对应的单元")
    private List<BuildUnitDto> children = new ArrayList<>();

    public BuildUnitDto(String label, String value) {
        this.label = label;
        this.value = value;
    }
}
