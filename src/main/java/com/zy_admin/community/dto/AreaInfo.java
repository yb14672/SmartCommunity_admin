package com.zy_admin.community.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通用树
 *
 * @author lvwei
 * @date 2022/12/05
 */
@ApiModel(description = "地区信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaInfo {
    /**
     * 名字
     */
    @ApiModelProperty("名字")
    private String name;
    /**
     * 值
     */
    @ApiModelProperty("值")
    private String value;
    /**
     * 地区代码
     */
    @ApiModelProperty("地区代码")
    private String regionCode="china";
}
