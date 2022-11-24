package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import com.zy_admin.community.entity.ZyOwner;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lvwei
 */
@ApiModel(description = "报修信息列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairAllDto  {
    /**
     * 获取报修信息集合
     */
    @ApiModelProperty("获取报修信息集合")
    private List<RepairDto> repairDtoList;

    /**
     * 获取分页对象
     */
    @ApiModelProperty("获取分页对象")
    private Pageable pageable;
}
