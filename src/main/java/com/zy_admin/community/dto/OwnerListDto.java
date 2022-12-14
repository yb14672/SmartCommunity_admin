package com.zy_admin.community.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 14208
 */
@ApiModel(description = "业主信息+地址")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerListDto  {
    /**
     *  绑定ID
     */
    @ApiModelProperty("绑定ID")
    private String ownerRoomId;
    /**
     * 小区名
     */
    @ApiModelProperty("小区名")
    private String communityName;
    /**
     * 楼栋名
     */
    @ApiModelProperty("楼栋名")
    private String buildingName;
    /**
     * 单元名
     */
    @ApiModelProperty("单元名")
    private String unitName;
    /**
     * 房间名
     */
    @ApiModelProperty("房间名")
    private String roomName;

    //业主id
    @ApiModelProperty("业主id")
    private Long ownerId;
    //昵称
    @ApiModelProperty("昵称")
    private String ownerNickname;
    //真实姓名
    @ApiModelProperty("真实姓名")
    private String ownerRealName;
    //性别unknow未知male男female女
    @ApiModelProperty("性别unknow未知male男female女")
    private String ownerGender;
    //年龄
    @ApiModelProperty("年龄")
    private Integer ownerAge;
    //身份证号码
    @ApiModelProperty("身份证号码")
    private String ownerIdCard;
    //手机号码
    @ApiModelProperty("手机号码")
    private String ownerPhoneNumber;
    //openid
    @ApiModelProperty("openid")
    private String ownerOpenId;
    //微信号
    @ApiModelProperty("微信号")
    private String ownerWechatId;
    //qq号码
    @ApiModelProperty("qq号码")
    private String ownerQqNumber;
    //出生日期
    @ApiModelProperty("出生日期")
    private String ownerBirthday;
    //头像
    @ApiModelProperty("头像")
    private String ownerPortrait;
    //个性签名
    @ApiModelProperty("个性签名")
    private String ownerSignature;
    //禁用状态enable启用-disable禁用
    @ApiModelProperty("禁用状态enable启用-disable禁用")
    private String ownerStatus;
    //注册方式（weChat微信-app-web）
    @ApiModelProperty("注册方式（weChat微信-app-web）")
    private String ownerLogonMode;
    //业主类型
    @ApiModelProperty("业主类型")
    private String ownerType;
    //密码
    @ApiModelProperty("密码")
    private String ownerPassword;
    //创建者
    @ApiModelProperty("创建者")
    private String createBy;
    //创建时间
    @ApiModelProperty("创建时间")
    private String createTime;
    //更新者
    @ApiModelProperty("更新者")
    private String updateBy;
    //更新时间
    @ApiModelProperty("更新时间")
    private String updateTime;
    //备注
    @ApiModelProperty("备注")
    private String remark;

}
