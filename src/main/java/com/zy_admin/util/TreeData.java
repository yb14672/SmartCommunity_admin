package com.zy_admin.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 描述
 * @author: xnylh
 * @createDate: 2022/11/21 0021 22:51
 */
@Data
public class TreeData {

    private List<RoomTree> menuList = new ArrayList<>();

    public TreeData(List<RoomTree> menuList){
        this.menuList = menuList;
    }

    /**
     * 获取根节点
     * @return
     */
    private List<RoomTree> getRootNode(){
        List<RoomTree> rootNode = new ArrayList<>();

        if(menuList.size() > 99){
            for (RoomTree menu : menuList) {
                if ("0".equals(menu.getId())) {
                    rootNode.add(menu);
                }
            }
        }else {
            for (int i =0 ; i <= menuList.size() - 1; i++) {
                if(menuList.get(i).getParentId().equals("0")){
                    rootNode.add(menuList.get(i));
                }else {
                    boolean f = true;
                    for (int j = menuList.size() - 1; j >= 0; j--) {
                        if (menuList.get(i).getParentId().equals(menuList.get(j).getId())) {
                            f = false;
                        }
                    }
                    if(f){
                        rootNode.add(menuList.get(i));
                    }
                }
            }
        }

        return rootNode;
    }

    /**
     * 构建子树
     * @param rootNode 子节点
     * @return
     */
    private RoomTree buildChildren(RoomTree rootNode){
        List<RoomTree> childrenTree = new ArrayList<>();
        for (RoomTree menu : menuList) {
            if (menu.getParentId().equals(rootNode.getId())){
                childrenTree.add(buildChildren(menu));
            }
        }
        rootNode.setChildNode(childrenTree);
        return rootNode;
    }

    /**
     * 构建树
     * @return
     */
    public List<RoomTree> buildTree(){
        List<RoomTree> menus = getRootNode();
        for (RoomTree menu : menus) {
            buildChildren(menu);
        }
        return menus;
    }
}
