package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 业主列表
 *
 * @author 14208
 * @date 2022/11/22
 */
@ApiModel(description = "业主列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {
    /**
     * 分页对象
     */
    @ApiModelProperty("分页对象")
    private Pageable pageable;
    /**
     * 所有者列表dto列表
     */
    @ApiModelProperty("所有者列表dto列表")
    private List<OwnerListDto> ownerListDtoList;
}
