package com.zy_admin.community.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访客邀请 (ZyVisitor)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:04
 */
@ApiModel(description = "访客邀请 (ZyVisitor)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyVisitor extends Model<ZyVisitor> {
    /**
     * id
     */
    @ApiModelProperty("id")
    private String visitorId;
    /**
     * 小区id
     */
    @ApiModelProperty("小区id")
    private String communityId;
    /**
     * 访客姓名
     */
    @ApiModelProperty("访客姓名")
    private String visitorName;
    /**
     * 访客手机号
     */
    @ApiModelProperty("访客手机号")
    private String visitorPhoneNumber;
    /**
     * 到访时间
     */
    @ApiModelProperty("到访时间")
    private String visitorDate;
    /**
     * 创建人id
     */
    @ApiModelProperty("创建人id")
    private String createById;
    /**
     * 创建人openid
     */
    @ApiModelProperty("创建人openid")
    private String createByOpenId;
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
     * 访客操作
     */
    @ApiModelProperty("访客操作")
    private String status;
}
