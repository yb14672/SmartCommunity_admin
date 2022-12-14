package com.zy_admin.community.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业主 (ZyOwner)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@ApiModel(description = "业主 (ZyOwner)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyOwner extends Model<ZyOwner> {
    /**
     * 业主id
     */
    @ApiModelProperty("业主id")
    @TableId
    private String ownerId;
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    @ExcelProperty("昵称")
    private String ownerNickname;
    /**
     * 真实姓名
     */
    @ApiModelProperty("真实姓名")
    @ExcelProperty("真实姓名")
    private String ownerRealName;
    /**
     * 性别unknow未知male男female女
     */
    @ApiModelProperty("性别unknow未知male男female女")
    @ExcelProperty("性别")
    private String ownerGender;
    /**
     * 年龄
     */
    @ApiModelProperty("年龄")
    @ExcelProperty("年龄")
    private Integer ownerAge;
    /**
     * 身份证号码
     */
    @ApiModelProperty("身份证号码")
    @ExcelProperty("身份证号码")
    private String ownerIdCard;
    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    @ExcelProperty("手机号码")
    private String ownerPhoneNumber;
    /**
     * openid
     */
    @ApiModelProperty("openid")
    private String ownerOpenId;
    /**
     * 微信号
     */
    @ApiModelProperty("微信号")
    private String ownerWechatId;
    /**
     * qq号码
     */
    @ApiModelProperty("qq号码")
    private String ownerQqNumber;
    /**
     * 出生日期
     */
    @ApiModelProperty("出生日期")
    private String ownerBirthday;
    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String ownerPortrait;
    /**
     * 个性签名
     */
    @ApiModelProperty("个性签名")
    private String ownerSignature;
    /**
     * 禁用状态enable启用-disable禁用
     */
    @ApiModelProperty("禁用状态enable启用-disable禁用")
    private String ownerStatus;
    /**
     * 注册方式（weChat微信-app-web）
     */
    @ApiModelProperty("注册方式（weChat微信-app-web）")
    private String ownerLogonMode;
    /**
     * 业主类型
     */
    @ApiModelProperty("业主类型")
    private String ownerType;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String ownerPassword;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    @ExcelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @ExcelProperty("创建时间")
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
    @ExcelProperty("备注")
    private String remark;
}

