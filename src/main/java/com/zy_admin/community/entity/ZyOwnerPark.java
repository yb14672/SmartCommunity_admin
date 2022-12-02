package com.zy_admin.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 房屋绑定表 (ZyOwnerPark)实体类
 *
 * @author makejava
 * @since 2022-12-01 15:18:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyOwnerPark implements Serializable {
    private static final long serialVersionUID = -93830376049597730L;
    /**
     * 房屋绑定id
     */
    private Long ownerParkId;
    /**
     * 车位id
     */
    private Long parkId;
    /**
     * 业主id
     */
    private Long ownerId;
    /**
     * 绑定状态（0审核中 1绑定 2审核失败）
     */
    private String parkOwnerStatus;
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

