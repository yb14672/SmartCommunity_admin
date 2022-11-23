package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author fangqian
 */
@ApiModel(description = "小区信息+地址")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyCommunityDto {
    /**
     * 显示数据
     */
    @ApiModelProperty("显示数据")
    private List<CommunityDto> zyCommunityList;
    /**
     * 分页条件
     */
    @ApiModelProperty("分页条件")
    private Pageable pageable;

}
