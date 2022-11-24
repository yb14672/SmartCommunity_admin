package com.zy_admin.community.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 投诉建议 (ZyComplaintSuggest)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@ApiModel(description = "投诉建议 (ZyComplaintSuggest)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyComplaintSuggest extends Model<ZyComplaintSuggest> {
    /**
     * id
     */
    @ApiModelProperty("id")
    private String complaintSuggestId;
    /**
     * 小区id
     */
    @ApiModelProperty("小区id")
    private String communityId;
    /**
     * 类型(投诉、建议)
     */
    @ApiModelProperty("类型(投诉、建议)")
    private String complaintSuggestType;
    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String complaintSuggestContent;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTime;
    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private String updateTime;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 投诉人ID
     */
    @ApiModelProperty("投诉人ID")
    private String userId;
}

