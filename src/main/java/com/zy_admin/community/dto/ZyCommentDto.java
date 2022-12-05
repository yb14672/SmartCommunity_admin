package com.zy_admin.community.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论对象 zy_comment
 * @description 存储评论信息
 * @author yb14672
 * @date 2022-11-23
 */
@ApiModel(description = "评论对象 zy_comment 存储评论信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZyCommentDto {

    /**
     * 评论id
     */
    @ApiModelProperty("评论id")
    @TableId
    @ExcelProperty("评论id")
    private String commentId;

    /**
     * 内容
     */
    @ApiModelProperty("内容")
    @ExcelProperty("内容")
    private String content;

    /**
     * 父级ID
     */
    @ApiModelProperty("父级ID")
    @ExcelProperty("父级ID")
    private String parentId;

    /**
     * 删除状态0默认1删除
     */
    @ApiModelProperty("删除状态0默认1删除")
    @ExcelIgnore
    private Integer delFlag;

    /**
     * 社区互动ID
     */
    @ApiModelProperty("社区互动ID")
    @ExcelProperty("社区互动ID")
    private String interactionId;

    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    @ExcelProperty("创建人ID")
    private String userId;

    /**
     * 业主名称
     */
    @ApiModelProperty("业主名称")
    @ExcelProperty("业主名称")
    private String ownerName;

    /**
     * 业主昵称
     */
    @ApiModelProperty("业主昵称")
    @ExcelProperty("业主昵称")
    private String ownerNickName;

    /**
     * 业主头像
     */
    @ApiModelProperty("业主头像")
    @ExcelProperty("业主头像")
    private String ownerPortrait;

    /**
     * 业主姓名(被回复者姓名)
     */
    @ApiModelProperty("被回复者姓名")
    @ExcelProperty("被回复者姓名")
    private String passiveOwnerName;

    /**
     * 子级评论
     */
    @ApiModelProperty("评论发表时间")
    @ExcelProperty("评论发表时间")
    private String createTime;
}
