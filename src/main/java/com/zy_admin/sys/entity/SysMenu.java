package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 菜单权限表(SysMenu)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:39
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends Model<SysMenu> {
    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    @TableId
    private Long menuId;
    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String menuName;
    /**
     * 父菜单ID
     */
    @ApiModelProperty("父菜单ID")
    private Long parentId;
    /**
     * 显示顺序
     */
    @ApiModelProperty("显示顺序")
    private Integer orderNum;
    /**
     * 路由地址
     */
    @ApiModelProperty("路由地址")
    private String path;
    /**
     * 组件路径
     */
    @ApiModelProperty("组件路径")
    private String component;
    /**
     * 是否为外链（0是 1否）
     */
    @ApiModelProperty("是否为外链（0是 1否）")
    private Integer isFrame;
    /**
     * 是否缓存（0缓存 1不缓存）
     */
    @ApiModelProperty("是否缓存（0缓存 1不缓存）")
    private Integer isCache;
    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @ApiModelProperty("菜单类型（M目录 C菜单 F按钮）")
    private String menuType;
    /**
     * 菜单状态（0显示 1隐藏）
     */
    @ApiModelProperty("菜单状态（0显示 1隐藏）")
    private String visible;
    /**
     * 菜单状态（0正常 1停用）
     */
    @ApiModelProperty("菜单状态（0正常 1停用）")
    private String status;
    /**
     * 权限标识
     */
    @ApiModelProperty("权限标识")
    private String perms;
    /**
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
    private String icon;
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
        return this.menuId;
    }
}

