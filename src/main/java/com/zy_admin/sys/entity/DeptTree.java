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
//    /**
//     * 部门id
//     */
//    @ApiModelProperty("部门ID")
//    private Long deptId;
//    /**
//     * 父部门id
//     */
//    @ApiModelProperty("部门ID")
//    private Long parentId;
//    /**
//     * 祖级列表
//     */
//    @ApiModelProperty("部门ID")
//    private String ancestors;
//    /**
//     * 部门名称
//     */
//    @ApiModelProperty("部门ID")
//    private String deptName;
//    /**
//     * 显示顺序
//     */
//    @ApiModelProperty("部门ID")
//    private Integer orderNum;
//    /**
//     * 负责人
//     */
//    @ApiModelProperty("部门ID")
//    private String leader;
//    /**
//     * 联系电话
//     */
//    @ApiModelProperty("部门ID")
//    private String phone;
//    /**
//     * 邮箱
//     */
//    @ApiModelProperty("部门ID")
//    private String email;
//    /**
//     * 部门状态（0正常 1停用）
//     */
//    @ApiModelProperty("部门ID")
//    private String status;
//    /**
//     * 删除标志（0代表存在 2代表删除）
//     */
//    @ApiModelProperty("部门ID")
//    private String delFlag;
//    /**
//     * 创建者
//     */
//    @ApiModelProperty("部门ID")
//    private String createBy;
//    /**
//     * 创建时间
//     */
//    @ApiModelProperty("创建时间")
//    private String createTime;
//    /**
//     * 更新者
//     */
//    @ApiModelProperty("更新者")
//    private String updateBy;
//    /**
//     * 更新时间
//     */
//    @ApiModelProperty("更新时间")
//    private String updateTime;
    /**
     * 子菜单列表
     */
    @ApiModelProperty("子菜单列表")
    private List<DeptTree> children;
}
