package com.zy_admin.community.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论表(ZyComment)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
@ApiModel(description = "评论表(ZyComment)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyComment extends Model<ZyComment> {
    /**
     * id
     */
    @ApiModelProperty("id")
    private Long commentId;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;
    /**
     * 更新者ID
     */
    @ApiModelProperty("更新者ID")
    private Long updateBy;
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
     * 父级ID
     */
    @ApiModelProperty("父级ID")
    private Long parentId;
    /**
     * 删除状态0默认1删除
     */
    @ApiModelProperty("删除状态0默认1删除")
    private Integer delFlag;
    /**
     * 社区互动ID
     */
    @ApiModelProperty("社区互动ID")
    private Long interactionId;
    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    private Long userId;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 划属Id
     */
    @ApiModelProperty("划属Id")
    private Long rootId;

}

