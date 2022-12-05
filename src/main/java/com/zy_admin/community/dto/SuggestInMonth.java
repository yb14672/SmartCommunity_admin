package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyComplaintSuggest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 一个月内的投诉建议
 *
 * @author yb14672
 * Time:2022 - 2022/12/4 - 22:58
 **/
@ApiModel(description = "一个月内的投诉建议")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestInMonth extends ZyComplaintSuggest {
    /**
     * 小区名
     */
    @ApiModelProperty("小区名")
    private String communityName;
}
