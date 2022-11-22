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
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("部门树")
public class DeptTree extends SysDept{
    /**
     * 子菜单列表
     */
    @ApiModelProperty("子菜单列表")
    private List<DeptTree> children;
}
