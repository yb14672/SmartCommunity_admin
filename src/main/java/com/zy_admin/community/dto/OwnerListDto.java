package com.zy_admin.community.dto;

import com.zy_admin.community.entity.ZyOwner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 14208
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerListDto  {
    /**
     *  绑定ID
     */
    private String ownerRoomId;
    /**
     * 小区名
     */
    private String communityName;
    /**
     * 楼栋名
     */
    private String buildingName;
    /**
     * 单元名
     */
    private String unitName;
    /**
     * 房间名
     */
    private String roomName;

    //业主id
    private Long ownerId;
    //昵称
    private String ownerNickname;
    //真实姓名
    private String ownerRealName;
    //性别unknow未知male男female女
    private String ownerGender;
    //年龄
    private Integer ownerAge;
    //身份证号码
    private String ownerIdCard;
    //手机号码
    private String ownerPhoneNumber;
    //openid
    private String ownerOpenId;
    //微信号
    private String ownerWechatId;
    //qq号码
    private String ownerQqNumber;
    //出生日期
    private String ownerBirthday;
    //头像
    private String ownerPortrait;
    //个性签名
    private String ownerSignature;
    //禁用状态enable启用-disable禁用
    private String ownerStatus;
    //注册方式（weChat微信-app-web）
    private String ownerLogonMode;
    //业主类型
    private String ownerType;
    //密码
    private String ownerPassword;
    //创建者
    private String createBy;
    //创建时间
    private String createTime;
    //更新者
    private String updateBy;
    //更新时间
    private String updateTime;
    //备注
    private String remark;

}
