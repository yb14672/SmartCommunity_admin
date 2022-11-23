package com.zy_admin.sys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 菜单树
 * @author 吕蔚霖
 */
@ApiModel(description = "菜单树")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTree{
    /**
     * 菜单id
     */
    @ApiModelProperty("菜单id")
    private String menuId;
    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String menuName;
    /**
     * 父级菜单id
     */
    @ApiModelProperty("父级菜单id")
    private String parentId;
    /**
     * 菜单顺序
     */
    @ApiModelProperty("菜单顺序")
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
     * 子菜单列表
     */
    @ApiModelProperty("子菜单列表")
    private List<MenuTree> children;
}
