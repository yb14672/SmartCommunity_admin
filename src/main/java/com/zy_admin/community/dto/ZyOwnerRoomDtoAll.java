package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 业主房间dto
 *
 * @author yb14672
 * @date 2022/11/22
 */
@ApiModel(description = "业主房间关联列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyOwnerRoomDtoAll {
    /**
     * 业主房间dto列表
     */
    @ApiModelProperty("业主房间dto列表")
    private List<ZyOwnerRoomDto> zyOwnerRoomDtoList;
    /**
     * 可分页
     */
    @ApiModelProperty("可分页")
    private Pageable pageable;
}
