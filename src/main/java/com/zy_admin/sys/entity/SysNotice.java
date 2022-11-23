package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通知公告表(SysNotice)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@ApiModel(description = "通知公告表(SysNotice)表实体类")
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysNotice extends Model<SysNotice> {
    /**
     * 公告ID
     */
    @ApiModelProperty("公告ID")
    private Integer noticeId;
    /**
     * 公告标题
     */
    @ApiModelProperty("公告标题")
    private String noticeTitle;
    /**
     * 公告类型（1通知 2公告）
     */
    @ApiModelProperty("公告类型（1通知 2公告）")
    private String noticeType;
    /**
     * 公告内容
     */
    @ApiModelProperty("公告内容")
    private Object noticeContent;
    /**
     * 公告状态（0正常 1关闭）
     */
    @ApiModelProperty("公告状态（0正常 1关闭）")
    private String status;
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
    /**
     * 获取主键值
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.noticeId;
    }
}

