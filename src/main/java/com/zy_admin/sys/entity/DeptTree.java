package com.zy_admin.sys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 屈羽星
 */
@ApiModel(description = "部门树")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptTree extends SysDept{
    /**
     * 子菜单列表
     */
    @ApiModelProperty("子菜单列表")
    private List<DeptTree> children;
}
