package com.zy_admin.community.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 区域信息列表
 *
 * @author yb14672
 * Time:2022/12/5 - 19:41
 */
@ApiModel(description = "区域信息列表")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaInfoList {
    /**
     * 区域信息列表
     */
    @ApiModelProperty("区域信息列表")
    private List<AreaInfo> areaInfoList;
    /**
     * 地区代码
     */
    @ApiModelProperty("地区代码")
    private String regionCode = "china";
}
