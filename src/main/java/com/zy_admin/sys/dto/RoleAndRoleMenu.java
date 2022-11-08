package com.zy_admin.sys.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * @author ZHANGYOUWEI
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAndRoleMenu {
    //角色ID
    private Long roleId;
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private String roleSort;
    //数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
    private String dataScope;
    //菜单树选择项是否关联显示
    private String menuCheckStrictly;
    //部门树选择项是否关联显示
    private String deptCheckStrictly;
    //角色状态（0正常 1停用）
    private String status;
    //删除标志（0代表存在 2代表删除）
    private String delFlag;
    //创建者
    private String createBy;
    //创建时间
    private String createTime;
    //更新者
    private String updateBy;
    //更新时间
    private String updateTime;
    //备注
    private String remark;
    //menuId集合
    private int[] menuIds;


}
