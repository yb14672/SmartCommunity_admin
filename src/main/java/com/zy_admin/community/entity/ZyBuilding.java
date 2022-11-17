package com.zy_admin.community.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 楼栋 (ZyBuilding)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:00
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyBuilding extends Model<ZyBuilding> {
    //楼栋id
    private String buildingId;
    //楼栋名称
    private String buildingName;
    //楼栋编码
    private String buildingCode;
    //楼栋面积
    private Double buildingAcreage;
    //小区id
    private Long communityId;
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


    @Override
    public String toString() {
        return "ZyBuilding{" +
                "buildingId='" + buildingId + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", buildingCode='" + buildingCode + '\'' +
                ", buildingAcreage=" + buildingAcreage +
                ", communityId=" + communityId +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}

