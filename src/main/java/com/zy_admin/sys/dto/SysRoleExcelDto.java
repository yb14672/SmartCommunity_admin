package com.zy_admin.sys.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色信息表(SysRoleExcelDto)表实体类
 *
 * @author makejava
 * @since 2022-11-01 19:49:40
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleExcelDto extends Model<SysRoleExcelDto> {
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
    private String createTime;
    /**
     * 更新者
     */
    @ExcelProperty("更新者")
    private String updateBy;
    /**
     * 更新时间
     */
    @ExcelProperty("更新时间")
    private String updateTime;
    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String remark;
    /**
     * 结束开始时间
     */
    @ExcelIgnore
    private String endCreateTime;
    /**
     * 菜单组
     */
    @ExcelIgnore
    private Long[] menuIds;
    /**
     * 部门组（数据权限）
     */
    @ExcelIgnore
    private Long[] deptIds;
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

