package com.zy_admin.sys.dto;

import com.zy_admin.sys.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author yb14672
 */
@ApiModel(description = "用户信息+岗位、角色id")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends SysUser {
    /**
     * 获取岗位id
     */
    @ApiModelProperty("获取岗位id")
    Integer postId;
    /**
     * 获取角色id
     */
    @ApiModelProperty("获取角色id")
    Integer roleId;
}
