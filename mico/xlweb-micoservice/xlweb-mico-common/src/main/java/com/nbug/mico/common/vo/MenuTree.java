package com.nbug.mico.common.vo;

import cn.hutool.core.collection.CollUtil;
import com.nbug.mico.common.response.MenusResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单树

 */
public class MenuTree {

    private List<MenusResponse> menuList = new ArrayList<MenusResponse>();

    public MenuTree(List<MenusResponse> menuList) {
        this.menuList = menuList;
    }

    //建立树形结构
    public List<MenusResponse> buildTree(){
        List<MenusResponse> treeMenus = new ArrayList<MenusResponse>();
        for(MenusResponse menuNode : getRootNode()) {
            menuNode = buildChildTree(menuNode);
            treeMenus.add(menuNode);
        }
        return sortList(treeMenus);
//        return treeMenus;
    }

    // 排序
    private List<MenusResponse> sortList(List<MenusResponse> treeMenus) {
        treeMenus = treeMenus.stream().sorted(Comparator.comparing(MenusResponse::getSort).reversed()).collect(Collectors.toList());
        treeMenus.forEach(e -> {
            if (CollUtil.isNotEmpty(e.getChildList())) {
                e.setChildList(sortList(e.getChildList()));
            }
        });
        return treeMenus;
    }

    //递归，建立子树形结构
    private MenusResponse buildChildTree(MenusResponse pNode){
        List<MenusResponse> childMenus = new ArrayList<MenusResponse>();
        for(MenusResponse menuNode : menuList) {
            if(menuNode.getPid().equals(pNode.getId())) {
                childMenus.add(buildChildTree(menuNode));
            }
        }
        pNode.setChildList(childMenus);
        return pNode;
    }

    //获取根节点
    private List<MenusResponse> getRootNode() {
        List<MenusResponse> rootMenuLists = new  ArrayList<MenusResponse>();
        for(MenusResponse menuNode : menuList) {
            if(menuNode.getPid().equals(0)) {
                rootMenuLists.add(menuNode);
            }
        }
        return rootMenuLists;
    }

}
