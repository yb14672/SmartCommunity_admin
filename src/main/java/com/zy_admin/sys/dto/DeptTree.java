package com.zy_admin.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门树状菜单
 * @author xiaozhao
 */
@ApiModel(description = "部门树状菜单")
public class DeptTree {
    /**
     * 流程：数据库查出的菜单记录装在承载菜单的列表中
     * 构建树（获取根节点，遍历根节点，对每一个根节点构建子树）---得到最终树形菜单
     * 承载菜单的列表
     */
    @ApiModelProperty("流程：数据库查出的菜单记录装在承载菜单的列表中 构建树（获取根节点，遍历根节点，对每一个根节点构建子树）---得到最终树形菜单 承载菜单的列表")
    private List<DeptTreeDto> deptList = new ArrayList<>();
    public DeptTree(List<DeptTreeDto> deptList) {
        this.deptList = deptList;
    }
    /**
     * 获取根节点
     *
     * @return
     */
    private List<DeptTreeDto> getRootNode() {
        List<DeptTreeDto> rootNode = new ArrayList<>();
        if (deptList.size() > 99) {
            for (DeptTreeDto dept : deptList) {
                if ("0".equals(dept.getParentId())) {
                    rootNode.add(dept);
                }
            }
        } else {
            for (int i = deptList.size() - 1; i >= 0; i--) {
                if (deptList.get(i).getParentId().equals("0")) {
                    rootNode.add(deptList.get(i));
                } else {
                    boolean f = true;
                    for (int j = deptList.size() - 1; j >= 0; j--) {
                        if (deptList.get(i).getParentId().equals(deptList.get(j).getDeptId())) {
                            f = false;
                        }
                    }
                    if (f) {
                        rootNode.add(deptList.get(i));
                    }
                }
            }
        }
        return rootNode;
    }
    /**
     * 构建子树
     *
     * @param rootNode 子节点
     * @return
     */
    private DeptTreeDto buildChildren(DeptTreeDto rootNode) {
        List<DeptTreeDto> childrenTree = new ArrayList<>();
        for (DeptTreeDto dept : deptList) {
            if (dept.getParentId().equals(rootNode.getDeptId())){
                childrenTree.add(buildChildren(dept));
            }
        }
        rootNode.setChildren(childrenTree);
        return rootNode;
    }
    /**
     * 构建树
     *
     * @return
     */
    public List<DeptTreeDto> buildTree() {
        List<DeptTreeDto> depts = getRootNode();
        for (DeptTreeDto dept : depts) {
            buildChildren(dept);
        }
        return depts;
    }
}