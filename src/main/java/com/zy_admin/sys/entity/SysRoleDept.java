package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 角色和部门关联表(SysRoleDept)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:41
 */
@SuppressWarnings("serial")
public class SysRoleDept extends Model<SysRoleDept> {
    //角色ID
    private Long roleId;
    //部门ID
    private Long deptId;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }
}

