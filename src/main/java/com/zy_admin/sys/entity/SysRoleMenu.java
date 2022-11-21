package com.zy_admin.sys.entity;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = 276605403972760818L;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID
     */
    private Long menuId;
}

