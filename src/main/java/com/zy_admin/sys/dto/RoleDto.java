package com.zy_admin.sys.dto;

import com.zy_admin.sys.entity.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author yb14672
 */
@ApiModel(description = "角色信息+是否有该权限")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto extends SysRole {
    /**
     * 用户是否有该权限
     */
    @ApiModelProperty("用户是否有该权限")
    private boolean userHave = false;
}
