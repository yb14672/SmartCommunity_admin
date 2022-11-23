package com.zy_admin.community.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 社区互动表(ZyCommunityInteraction)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@ApiModel(description = "社区互动表(ZyCommunityInteraction)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyCommunityInteraction extends Model<ZyCommunityInteraction> {
    /**
     * id
     */
    @ApiModelProperty("id")
    private String interactionId;
    /**
     * 小区ID
     */
    @ApiModelProperty("小区ID")
    private String communityId;
    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    private String createBy;
    /**
     * 更新者ID
     */
    @ApiModelProperty("更新者ID")
    private String updateBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private String updateTime;
    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;
    /**
     * 删除状态0默认1删除
     */
    @ApiModelProperty("删除状态0默认1删除")
    private Integer delFlag;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    private String userId;
}

