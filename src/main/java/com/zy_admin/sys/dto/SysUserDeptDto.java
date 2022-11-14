package com.zy_admin.sys.dto;

import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.sys.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserDeptDto extends SysUser {
    /**
     * 部门
     */
    private SysDept dept;
}