package com.zy_admin.sys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色和菜单关联表(SysRoleMenu)实体类
 *
 * @author makejava
 * @since 2022-11-08 14:56:27
 */
@ApiModel(description = "角色和菜单关联表(SysRoleMenu)实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleMenu implements Serializable {
    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 276605403972760818L;
    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    private Long roleId;
    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    private Long menuId;
}

