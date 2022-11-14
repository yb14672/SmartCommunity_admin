package com.zy_admin.util;


import com.zy_admin.sys.entity.DeptTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaozhao
 */
public class Tree2 {

    /**
     * 流程：数据库查出的菜单记录装在承载菜单的列表中
     * 构建树（获取根节点，遍历根节点，对每一个根节点构建子树）---得到最终树形菜单
     * 承载菜单的列表
     */
    private List<DeptTree> deptList = new ArrayList<>();

    public Tree2(List<DeptTree> deptList) {
        this.deptList = deptList;
    }

    /**
     * 获取根节点
     *
     * @return
     */
    private List<DeptTree> getRootNode() {
        List<DeptTree> rootNode = new ArrayList<>();
        if (deptList.size() > 99) {
            for (DeptTree dept : deptList) {
                if ("0".equals(dept.getParentId())) {
                    rootNode.add(dept);
                }
            }
        } else {
            for (int i = 0; i <=deptList.size() - 1 ; i++) {
                if (deptList.get(i).getParentId().equals("0")) {
                    rootNode.add(deptList.get(i));
                } else {
                    boolean f = true;
                    for (int j = 0; j <= deptList.size()-1; j++) {
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
    private DeptTree buildChildren(DeptTree rootNode) {
        List<DeptTree> childrenTree = new ArrayList<>();
        for (DeptTree dept : deptList) {
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
    public List<DeptTree> buildTree() {
        List<DeptTree> depts = getRootNode();
        for (DeptTree dept : depts) {
            buildChildren(dept);
        }
        return depts;
    }
}