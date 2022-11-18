package com.zy_admin.sys.dto;

import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.sys.entity.SysPost;
import com.zy_admin.sys.entity.SysRole;
import com.zy_admin.sys.entity.SysUser;

public class SysUserDto {
    private SysRole sysRole;

    private SysDept sysDept;

    private SysPost sysPost;

    private SysUser sysUser;

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public SysDept getSysDept() {
        return sysDept;
    }

    public void setSysDept(SysDept sysDept) {
        this.sysDept = sysDept;
    }

    public SysPost getSysPost() {
        return sysPost;
    }

    public void setSysPost(SysPost sysPost) {
        this.sysPost = sysPost;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    @Override
    public String toString() {
        return "SysUserDto{" +
                "sysRole=" + sysRole +
                ", sysDept=" + sysDept +
                ", sysPost=" + sysPost +
                ", sysUser=" + sysUser +
                '}';
    }
}
