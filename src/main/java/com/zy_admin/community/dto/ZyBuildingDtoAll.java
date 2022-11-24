package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Administrator
 */
@ApiModel(description = "楼栋信息列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyBuildingDtoAll {
    /**
     * 获取楼栋集合
     */
    @ApiModelProperty("获取楼栋集合")
    private List<ZyBuildingDto> zyBuildingList;
    /**
     * 获取分页对象
     */
    @ApiModelProperty("获取分页对象")
    private Pageable pageable;
}
