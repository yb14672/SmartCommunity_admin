package com.zy_admin.community.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 访客邀请 (ZyVisitor)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:04
 */
@SuppressWarnings("serial")
public class ZyVisitor extends Model<ZyVisitor> {
    /**
     * id
     */
    private Long visitorId;
    /**
     * 小区id
     */
    private Long communityId;
    /**
     * 访客姓名
     */
    private String visitorName;
    /**
     * 访客手机号
     */
    private String visitorPhoneNumber;
    /**
     * 到访时间
     */
    private String visitorString;
    /**
     * 创建人id
     */
    private Long createById;
    /**
     * 创建人openid
     */
    private String createByOpenId;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 备注
     */
    private String remark;
}
