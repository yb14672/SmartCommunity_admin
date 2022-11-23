package com.zy_admin.sys.dto;


import com.zy_admin.sys.entity.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @author ZHANGYOUWEI
 */
@ApiModel(description = "角色信息+权限ID列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAndRoleMenu extends SysRole {
    /**
     * menuId集合
     */
    @ApiModelProperty("menuId集合")
    private Integer[] menuIds;
}
