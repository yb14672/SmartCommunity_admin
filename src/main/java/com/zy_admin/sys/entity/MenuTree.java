package com.zy_admin.sys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 菜单树
 * @author 吕蔚霖
 */
@ApiModel(description = "菜单树")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTree extends SysMenu{

    /**
     * 子菜单列表
     */
    @ApiModelProperty("子菜单列表")
    private List<MenuTree> children;
}
