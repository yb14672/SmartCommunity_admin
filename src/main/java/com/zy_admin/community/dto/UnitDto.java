package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 14208
 */
@ApiModel(description = "单元列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDto {
    /**
     * 单元集合dto
     */
    @ApiModelProperty("单元集合dto")
    private List<UnitListDto> unitListDtos;
    /**
     * 分页对象
     */
    @ApiModelProperty("分页对象")
    private Pageable pageable;
}
