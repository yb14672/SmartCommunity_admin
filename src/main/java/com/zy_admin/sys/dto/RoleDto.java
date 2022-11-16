package com.zy_admin.sys.dto;

import com.zy_admin.sys.entity.SysRole;
import lombok.*;

/**
 * @author yb14672
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto extends SysRole {
    /**
     * 用户是否有该权限
     */
    private boolean userHave = false;
}
