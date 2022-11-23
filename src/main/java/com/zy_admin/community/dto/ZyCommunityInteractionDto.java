package com.zy_admin.community.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zy_admin.community.entity.ZyFiles;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 社区互动对象 zy_community_interaction
 *
 * @author yb14672
 * @date 2022-11-23
 */
@ApiModel(description = "社区互动对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZyCommunityInteractionDto{

    /**
     * id
     */
    @ApiModelProperty("id")
    @TableId
    @ExcelIgnore
    private String interactionId;

    /**
     * 小区ID
     */
    @ApiModelProperty("小区ID")
    @ExcelProperty("小区ID")
    private String communityId;

    /**
     * 内容
     */
    @ApiModelProperty("内容")
    @ExcelProperty("内容")
    private String content;

    /**
     * 图片ID
     */
    @ApiModelProperty("图片ID")
    @ExcelProperty("图片ID")
    private String filesId;

    /**
     * 删除状态0默认1删除
     */
    @ApiModelProperty("删除状态0默认1删除")
    @ExcelIgnore
    private Integer delFlag;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    @ExcelProperty("用户昵称")
    private String ownerNickname;

    /**
     * 用户真实姓名
     */
    @ApiModelProperty("用户真实姓名")
    @ExcelProperty("用户真实姓名")
    private String ownerRealName;

    /**
     * 业主电话
     */
    @ApiModelProperty("业主电话")
    @ExcelProperty("业主电话")
    private String ownerPhoneNumber;

    /**
     * 图片地址list
     */
    @ApiModelProperty("图片地址list")
    @ExcelIgnore
    private List<String> urlList;

    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    @ExcelIgnore
    private String userId;

    /**
     * 文件表
     */
    @ApiModelProperty("文件表")
    @ExcelIgnore
    private ZyFiles[] zyFiles;

    /**
     * 评论表
     */
    @ApiModelProperty("评论表")
    @ExcelIgnore
    private List<ZyCommentDto> zyCommentList;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    @ExcelProperty("头像")
    private String avatar;
}
