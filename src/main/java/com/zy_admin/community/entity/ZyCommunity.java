package com.zy_admin.community.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小区 (ZyCommunityDto)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:01
 */
@ApiModel(description = "小区 (ZyCommunityDto)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyCommunity extends Model<ZyCommunity> {
    /**
     * 小区id
     */
    @ApiModelProperty("小区id")
    @TableId
    private String communityId;
    /**
     * 小区名称
     */
    @ApiModelProperty("小区名称")
    private String communityName;
    /**
     * 小区编码
     */
    @ApiModelProperty("小区编码")
    private String communityCode;
    /**
     * 省区划码
     */
    @ApiModelProperty("省区划码")
    private String communityProvenceCode;
    /**
     * 市区划码
     */
    @ApiModelProperty("市区划码")
    private String communityCityCode;
    /**
     * 区县区划码
     */
    @ApiModelProperty("区县区划码")
    private String communityTownCode;
    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    private String communityDetailedAddress;
    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private String communityLongitude;
    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    private String communityLatitude;
    /**
     * 物业id
     */
    @ApiModelProperty("物业id")
    private Long deptId;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer communitySort;
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
}

