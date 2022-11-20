package com.zy_admin.sys.dto;

import com.zy_admin.sys.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yb14672
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends SysUser {
    Integer postId;
    Integer roleId;
}
