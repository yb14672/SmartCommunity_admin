package com.zy_admin.sys.dto;

import com.zy_admin.sys.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author yb14672
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends SysUser {
    /**
     * 获取岗位id
     */
    Integer postId;
    /**
     * 获取角色id
     */
    Integer roleId;
}
