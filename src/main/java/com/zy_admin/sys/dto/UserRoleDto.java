package com.zy_admin.sys.dto;

import com.zy_admin.sys.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户分配角色详情
 * @author yb14672
 * Time:2022 - 2022/11/13 - 14:54
 **/
@ApiModel(description = "用户分配角色详情")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRoleDto extends SysUser {
    /**
     * 当前用户所拥有的权限
     */
    @ApiModelProperty("当前用户所拥有的权限")
    private List<RoleDto> roleList;
}
