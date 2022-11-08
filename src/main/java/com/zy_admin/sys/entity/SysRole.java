package com.zy_admin.sys.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色信息表(SysRole)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@SuppressWarnings("serial")
public class SysRole extends Model<SysRole> {
    /**
     * 角色ID
     */
    @ExcelProperty("角色ID")
    private Long roleId;
    /**
     * 角色名称
     */
    @ExcelProperty("角色名称")
    private String roleName;
    /**
     * 角色权限字符串
     */
    @ExcelProperty("角色权限字符串")
    private String roleKey;
    /**
     * 显示顺序
     */
    @ExcelProperty("显示顺序")
    private Integer roleSort;
    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    @ExcelProperty("数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    private String dataScope;
    /**
     * 菜单树选择项是否关联显示
     */
    @ExcelIgnore
    private Integer menuCheckStrictly;
    /**
     * 部门树选择项是否关联显示
     */
    @ExcelIgnore
    private Integer deptCheckStrictly;
    /**
     * 角色状态（0正常 1停用）
     */
    @ExcelProperty("角色状态（0正常 1停用）")
    private String status;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ExcelProperty("删除标志（0代表存在 2代表删除）")
    private String delFlag;
    /**
     * 创建者
     */
    @ExcelProperty("创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    private Date createTime;
    /**
     * 更新者
     */
    @ExcelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ExcelProperty("更新时间")
    private Date updateTime;
    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String remark;

    @ExcelIgnore
    private String endCreateTime;

    /** 菜单组 */
    @ExcelIgnore
    private Long[] menuIds;

    /** 部门组（数据权限） */
    @ExcelIgnore
    private Long[] deptIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public Integer getRoleSort() {
        return roleSort;
    }

    public void setRoleSort(Integer roleSort) {
        this.roleSort = roleSort;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    public Integer getMenuCheckStrictly() {
        return menuCheckStrictly;
    }

    public void setMenuCheckStrictly(Integer menuCheckStrictly) {
        this.menuCheckStrictly = menuCheckStrictly;
    }

    public Integer getDeptCheckStrictly() {
        return deptCheckStrictly;
    }

    public void setDeptCheckStrictly(Integer deptCheckStrictly) {
        this.deptCheckStrictly = deptCheckStrictly;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

