package com.zy_admin.sys.dto;

import com.zy_admin.sys.entity.SysDept;
import com.zy_admin.sys.entity.SysPost;
import com.zy_admin.sys.entity.SysRole;
import com.zy_admin.sys.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lvwei
 */
@ApiModel(description = "用户的所有信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDto {
    /**
     * 获取角色对象
     */
    @ApiModelProperty("获取角色对象")
    private SysRole sysRole;
    /**
     * 获取部门对象
     */
    @ApiModelProperty("获取部门对象")
    private SysDept sysDept;
    /**
     * 获取岗位对象
     */
    @ApiModelProperty("获取岗位对象")
    private SysPost sysPost;
    /**
     * 获取用户对象
     */
    @ApiModelProperty("获取用户对象")
    private SysUser sysUser;
}
