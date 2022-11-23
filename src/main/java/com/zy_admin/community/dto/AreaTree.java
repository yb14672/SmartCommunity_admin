package com.zy_admin.community.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成地区三级联动树形结构
 *
 * @author fangqian
 */
@ApiModel(description = "地区三级树形结构")
public class AreaTree {
    /**
     * 流程：数据库查出的菜单记录装在承载菜单的列表中
     * 构建树（获取根节点，遍历根节点，对每一个根节点构建子树）---得到最终树形菜单
     * 承载菜单的列表
     */
    @ApiModelProperty("子集地区")
    private List<AreaDto> areaList = new ArrayList<>();

    public AreaTree(List<AreaDto> areaList) {
        this.areaList = areaList;
    }

    /**
     * 获取根节点
     *
     * @return
     */
    private List<AreaDto> getRootNode() {
        List<AreaDto> rootNode = new ArrayList<>();
        if (areaList.size() > 99) {
            for (AreaDto area : areaList) {
                if ("0".equals(area.getParentid())) {
                    rootNode.add(area);
                }
            }
        } else {
            for (int i = 0; i <=areaList.size() - 1 ; i++) {
                if (areaList.get(i).getParentid().equals("0")) {
                    rootNode.add(areaList.get(i));
                } else {
                    boolean f = true;
                    for (int j = 0; j <= areaList.size()-1; j++) {
                        if (areaList.get(i).getParentid().equals(areaList.get(j).getParentid())) {
                            f = false;
                        }
                    }
                    if (f) {
                        rootNode.add(areaList.get(i));
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
    private AreaDto buildChildren(AreaDto rootNode) {
        List<AreaDto> childrenTree = new ArrayList<>();
        for (AreaDto area : areaList) {
            if (area.getParentid().equals(rootNode.getCode())){
                childrenTree.add(buildChildren(area));
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
    public List<AreaDto> buildTree() {
        List<AreaDto> areas = getRootNode();
        for (AreaDto area : areas) {
            buildChildren(area);
        }
        return areas;
    }
}
