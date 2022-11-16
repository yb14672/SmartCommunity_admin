package com.zy_admin.community.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 业主 (ZyOwner)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:02
 */
@SuppressWarnings("serial")
public class ZyOwner extends Model<ZyOwner> {
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
    private Date ownerBirthday;
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
    private Date createTime;
    //更新者
    private String updateBy;
    //更新时间
    private Date updateTime;
    //备注
    private String remark;


    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerNickname() {
        return ownerNickname;
    }

    public void setOwnerNickname(String ownerNickname) {
        this.ownerNickname = ownerNickname;
    }

    public String getOwnerRealName() {
        return ownerRealName;
    }

    public void setOwnerRealName(String ownerRealName) {
        this.ownerRealName = ownerRealName;
    }

    public String getOwnerGender() {
        return ownerGender;
    }

    public void setOwnerGender(String ownerGender) {
        this.ownerGender = ownerGender;
    }

    public Integer getOwnerAge() {
        return ownerAge;
    }

    public void setOwnerAge(Integer ownerAge) {
        this.ownerAge = ownerAge;
    }

    public String getOwnerIdCard() {
        return ownerIdCard;
    }

    public void setOwnerIdCard(String ownerIdCard) {
        this.ownerIdCard = ownerIdCard;
    }

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public String getOwnerOpenId() {
        return ownerOpenId;
    }

    public void setOwnerOpenId(String ownerOpenId) {
        this.ownerOpenId = ownerOpenId;
    }

    public String getOwnerWechatId() {
        return ownerWechatId;
    }

    public void setOwnerWechatId(String ownerWechatId) {
        this.ownerWechatId = ownerWechatId;
    }

    public String getOwnerQqNumber() {
        return ownerQqNumber;
    }

    public void setOwnerQqNumber(String ownerQqNumber) {
        this.ownerQqNumber = ownerQqNumber;
    }

    public Date getOwnerBirthday() {
        return ownerBirthday;
    }

    public void setOwnerBirthday(Date ownerBirthday) {
        this.ownerBirthday = ownerBirthday;
    }

    public String getOwnerPortrait() {
        return ownerPortrait;
    }

    public void setOwnerPortrait(String ownerPortrait) {
        this.ownerPortrait = ownerPortrait;
    }

    public String getOwnerSignature() {
        return ownerSignature;
    }

    public void setOwnerSignature(String ownerSignature) {
        this.ownerSignature = ownerSignature;
    }

    public String getOwnerStatus() {
        return ownerStatus;
    }

    public void setOwnerStatus(String ownerStatus) {
        this.ownerStatus = ownerStatus;
    }

    public String getOwnerLogonMode() {
        return ownerLogonMode;
    }

    public void setOwnerLogonMode(String ownerLogonMode) {
        this.ownerLogonMode = ownerLogonMode;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getOwnerPassword() {
        return ownerPassword;
    }

    public void setOwnerPassword(String ownerPassword) {
        this.ownerPassword = ownerPassword;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.ownerId;
    }
}

