package com.zy_admin.community.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小区 (ZyCommunityDto)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyCommunity extends Model<ZyCommunity> {
    //小区id
    @TableId
    private String communityId;
    //小区名称
    private String communityName;
    //小区编码
    private String communityCode;
    //省区划码
    private String communityProvenceCode;
    //市区划码
    private String communityCityCode;
    //区县区划码
    private String communityTownCode;
    //详细地址
    private String communityDetailedAddress;
    //经度
    private String communityLongitude;
    //纬度
    private String communityLatitude;
    //物业id
    private Long deptId;
    //排序
    private Integer communitySort;
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

