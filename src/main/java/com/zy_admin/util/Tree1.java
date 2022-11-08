package com.zy_admin.util;


import com.zy_admin.sys.entity.MenuTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaozhao
 */
public class Tree1 {

    /**
     * 流程：数据库查出的菜单记录装在承载菜单的列表中
     * 构建树（获取根节点，遍历根节点，对每一个根节点构建子树）---得到最终树形菜单
     * 承载菜单的列表
     */
    private List<MenuTree> menuList = new ArrayList<>();

    public Tree1(List<MenuTree> menuList) {
        this.menuList = menuList;
    }

    /**
     * 获取根节点
     *
     * @return
     */
    private List<MenuTree> getRootNode() {
        List<MenuTree> rootNode = new ArrayList<>();
        if (menuList.size() > 99) {
            for (MenuTree menu : menuList) {
                if ("0".equals(menu.getParentId())) {
                    rootNode.add(menu);
                }
            }
        } else {
            for (int i = menuList.size() - 1; i >= 0; i--) {
                if (menuList.get(i).getParentId().equals("0")) {
                    rootNode.add(menuList.get(i));
                } else {
                    boolean f = true;
                    for (int j = menuList.size() - 1; j >= 0; j--) {
                        if (menuList.get(i).getParentId().equals(menuList.get(j).getMenuId())) {
                            f = false;
                        }
                    }
                    if (f) {
                        rootNode.add(menuList.get(i));
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
    private MenuTree buildChildren(MenuTree rootNode) {
        List<MenuTree> childrenTree = new ArrayList<>();
        for (MenuTree menu : menuList) {
            if (menu.getParentId().equals(rootNode.getMenuId())){
                childrenTree.add(buildChildren(menu));
            }
        }
//        for (int i = menuList.size() - 1;i >= 0; i--){
//            if (menuList.get(i).getParentId().equals(rootNode.getMenuId())){
//                childrenTree.add(buildChildren(menuList.get(i)));
//            }
//        }
            rootNode.setChildren(childrenTree);
        return rootNode;
    }

    /**
     * 构建树
     *
     * @return
     */
    public List<MenuTree> buildTree() {
        List<MenuTree> menus = getRootNode();
        for (MenuTree menu : menus) {
            buildChildren(menu);
        }
        return menus;
    }
}