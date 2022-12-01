package com.zy_admin.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 房屋绑定记录表 (ZyOwnerParkRecord)实体类
 *
 * @author makejava
 * @since 2022-12-01 15:10:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyOwnerParkRecord implements Serializable {
    private static final long serialVersionUID = -30157378326104315L;
    /**
     * 车位绑定记录id
     */
    private Long recordId;
    /**
     * 车位id
     */
    private String ownerParkId;
    /**
     * 小区id
     */
    private Long communityId;
    /**
     * 业主id
     */
    private Long ownerId;
    /**
     * 绑定状态（0审核中 1绑定 2审核失败,3解绑）
     */
    private String parkBundingStatus;
    /**
     * 审核意见
     */
    private String recordAuditOpinion;
    /**
     * 审核人类型
     */
    private String recordAuditType;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;

}

