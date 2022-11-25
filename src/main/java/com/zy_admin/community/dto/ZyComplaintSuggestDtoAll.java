package com.zy_admin.community.dto;

import com.zy_admin.common.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "投诉建议列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyComplaintSuggestDtoAll {
    /**
     * 投诉建议集合
     */
    @ApiModelProperty("投诉建议集合")
    private List<ZyComplaintSuggestDto> zyComplaintSuggestDtoList;

    /**
     * 分页
     */
    @ApiModelProperty("分页")
    private Pageable pageable;
}
