package com.zy_admin.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 用户和角色关联表(SysUserRole)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:42
 */
@SuppressWarnings("serial")
public class SysUserRole extends Model<SysUserRole> {
    //用户ID
    private Long userId;
    //角色ID
    private Long roleId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.userId;
    }
}

