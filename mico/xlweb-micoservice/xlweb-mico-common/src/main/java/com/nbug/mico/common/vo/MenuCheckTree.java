package com.nbug.mico.common.vo;

import cn.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单选中树

 */
public class MenuCheckTree {

    private List<MenuCheckVo> menuList = new ArrayList<MenuCheckVo>();

    public MenuCheckTree(List<MenuCheckVo> menuList) {
        this.menuList = menuList;
    }

    //建立树形结构
    public List<MenuCheckVo> buildTree(){
        List<MenuCheckVo> treeMenus = new ArrayList<MenuCheckVo>();
        for(MenuCheckVo menuNode : getRootNode()) {
            menuNode = buildChildTree(menuNode);
            treeMenus.add(menuNode);
        }
        return sortList(treeMenus);
//        return treeMenus;
    }

    // 排序
    private List<MenuCheckVo> sortList(List<MenuCheckVo> treeMenus) {
        treeMenus = treeMenus.stream().sorted(Comparator.comparing(MenuCheckVo::getSort).reversed()).collect(Collectors.toList());
        treeMenus.forEach(e -> {
            if (CollUtil.isNotEmpty(e.getChildList())) {
                e.setChildList(sortList(e.getChildList()));
            }
        });
        return treeMenus;
    }

    //递归，建立子树形结构
    private MenuCheckVo buildChildTree(MenuCheckVo pNode){
        List<MenuCheckVo> childMenus = new ArrayList<MenuCheckVo>();
        for(MenuCheckVo menuNode : menuList) {
            if(menuNode.getPid().equals(pNode.getId())) {
                childMenus.add(buildChildTree(menuNode));
            }
        }
        pNode.setChildList(childMenus);
        return pNode;
    }

    //获取根节点
    private List<MenuCheckVo> getRootNode() {
        List<MenuCheckVo> rootMenuLists = new  ArrayList<MenuCheckVo>();
        for(MenuCheckVo menuNode : menuList) {
            if(menuNode.getPid().equals(0)) {
                rootMenuLists.add(menuNode);
            }
        }
        return rootMenuLists;
    }

}
