package com.zy_admin.sys.dto;

import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.sys.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author admin
 */
@ApiModel(description = "用户信息+对应部门信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserDeptDto extends SysUser {
    /**
     * 部门
     */
    @ApiModelProperty("部门")
    private SysDept dept;
}