package com.zy_admin.sys.dto;


import com.zy_admin.sys.entity.SysRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @author ZHANGYOUWEI
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAndRoleMenu extends SysRole {
    /**
     * menuId集合
     */
    private Integer[] menuIds;
}
