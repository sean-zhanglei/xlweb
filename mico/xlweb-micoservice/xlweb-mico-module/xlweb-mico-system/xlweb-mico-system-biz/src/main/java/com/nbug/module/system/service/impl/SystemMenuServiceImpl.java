package com.nbug.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.system.SystemMenu;
import com.nbug.mico.common.request.SystemMenuRequest;
import com.nbug.mico.common.request.SystemMenuSearchRequest;
import com.nbug.mico.common.utils.redis.RedisUtil;
import com.nbug.mico.common.vo.MenuCheckTree;
import com.nbug.mico.common.vo.MenuCheckVo;
import com.nbug.module.system.dal.SystemMenuDao;
import com.nbug.module.system.service.SystemMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SystemMenuServiceImpl 接口实现

 */
@Service
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuDao, SystemMenu> implements SystemMenuService {

    @Resource
    private SystemMenuDao dao;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 通过权限获取管理员可访问目录
     * @return List<SystemMenu>
     */
    @Override
    public List<SystemMenu> findCatalogueByPermission(List<String> permissionsList) {
        LambdaQueryWrapper<SystemMenu> lqw = Wrappers.lambdaQuery();
        lqw.eq(SystemMenu::getIsDelte, false);
        lqw.eq(SystemMenu::getIsShow, true);
        lqw.ne(SystemMenu::getMenuType, "A");
        lqw.in(SystemMenu::getPerms, permissionsList);
        lqw.groupBy(SystemMenu::getId);
        return dao.selectList(lqw);
    }

    /**
     * 获取所有菜单
     * @return List<SystemMenu>
     */
    @Override
    public List<SystemMenu> findAllCatalogue() {
        LambdaQueryWrapper<SystemMenu> lqw = Wrappers.lambdaQuery();
        lqw.eq(SystemMenu::getIsDelte, false);
        lqw.eq(SystemMenu::getIsShow, true);
        lqw.ne(SystemMenu::getMenuType, "A");
        return dao.selectList(lqw);
    }

    /**
     * 菜单列表
     * @param request 请求参数
     */
    @Override
    public List<SystemMenu> getAdminList(SystemMenuSearchRequest request) {
        LambdaQueryWrapper<SystemMenu> lqw = Wrappers.lambdaQuery();
        if (StrUtil.isNotEmpty(request.getName())) {
            lqw.like(SystemMenu::getName, request.getName());
        }
        if (StrUtil.isNotEmpty(request.getMenuType())) {
            lqw.eq(SystemMenu::getName, request.getMenuType());
        }
        lqw.eq(SystemMenu::getIsDelte, false);
        lqw.orderByDesc(SystemMenu::getSort);
        lqw.orderByAsc(SystemMenu::getId);
        return dao.selectList(lqw);
    }

    /**
     * 新增菜单
     * @param request 菜单参数
     * @return Boolean
     */
    @Override
    public Boolean add(SystemMenuRequest request) {
        if ("C".equals(request.getMenuType()) && StrUtil.isEmpty(request.getComponent())) {
            throw new XlwebException("菜单类型的组件路径不能为空");
        }
        if ("A".equals(request.getMenuType()) && StrUtil.isEmpty(request.getPerms())) {
            throw new XlwebException("按钮类型的权限表示不能为空");
        }
        SystemMenu systemMenu = new SystemMenu();
        request.setId(null);
        BeanUtils.copyProperties(request, systemMenu);
        boolean save = save(systemMenu);
        if (save) {
            redisUtil.delete(Constants.CACHE_LIST_KEY);
        }
        return save;
    }

    /**
     * 根据id删除菜单
     * @param id 菜单id
     * @return Boolean
     */
    @Override
    public Boolean deleteById(Integer id) {
        SystemMenu systemMenu = getInfoById(id);
        systemMenu.setIsDelte(true);
        if ("A".equals(systemMenu.getMenuType())) {
            boolean update = updateById(systemMenu);
            if (update) {
                redisUtil.delete(Constants.CACHE_LIST_KEY);
            }
            return update;
        }
        List<SystemMenu> childList = findAllChildListByPid(id);
        if (CollUtil.isEmpty(childList)) {
            boolean update = updateById(systemMenu);
            if (update) {
                redisUtil.delete(Constants.CACHE_LIST_KEY);
            }
            return update;
        }
        childList.forEach(e -> e.setIsDelte(true));
        childList.add(systemMenu);
        boolean updateBatch = updateBatchById(childList);
        if (updateBatch) {
            redisUtil.delete(Constants.CACHE_LIST_KEY);
        }
        return updateBatch;
    }

    /**
     * 修改菜单
     * @param request 菜单参数
     * @return Boolean
     */
    @Override
    public Boolean edit(SystemMenuRequest request) {
        if (ObjectUtil.isNull(request.getId())) {
            throw new XlwebException("系统菜单id不能为空");
        }
        if ("C".equals(request.getMenuType()) && StrUtil.isEmpty(request.getComponent())) {
            throw new XlwebException("菜单类型的组件路径不能为空");
        }
        if ("A".equals(request.getMenuType()) && StrUtil.isEmpty(request.getPerms())) {
            throw new XlwebException("按钮类型的权限表示不能为空");
        }
        SystemMenu systemMenu = new SystemMenu();
        BeanUtils.copyProperties(request, systemMenu);
        boolean update = updateById(systemMenu);
        if (update) {
            redisUtil.delete(Constants.CACHE_LIST_KEY);
        }
        return update;
    }

    /**
     * 获取菜单详情
     * @param id 菜单id
     * @return SystemMenu
     */
    @Override
    public SystemMenu getInfo(Integer id) {
        SystemMenu systemMenu = getInfoById(id);
        systemMenu.setIsDelte(null);
        systemMenu.setCreateTime(null);
        systemMenu.setUpdateTime(null);
        return systemMenu;
    }

    /**
     * 修改菜单显示状态
     * @param id 菜单id
     * @return Boolean
     */
    @Override
    public Boolean updateShowStatus(Integer id) {
        SystemMenu systemMenu = getInfoById(id);
        systemMenu.setIsShow(!systemMenu.getIsShow());
        boolean update = updateById(systemMenu);
        if (update) {
            redisUtil.delete(Constants.CACHE_LIST_KEY);
        }
        return update;
    }

    /**
     * 获取菜单缓存列表
     */
    @Override
    public List<SystemMenu> getCacheList() {
        if (redisUtil.exists(Constants.CACHE_LIST_KEY)) {
            return redisUtil.get(Constants.CACHE_LIST_KEY);
        }
        LambdaQueryWrapper<SystemMenu> lqw = Wrappers.lambdaQuery();
        lqw.eq(SystemMenu::getIsDelte, false);
        List<SystemMenu> systemMenuList = dao.selectList(lqw);
        redisUtil.set(Constants.CACHE_LIST_KEY, systemMenuList);
        return systemMenuList;
    }

    /**
     * 菜单缓存树
     * @return List
     */
    @Override
    public List<MenuCheckVo> getCacheTree() {
        List<SystemMenu> menuList = getCacheList();
        List<MenuCheckVo> voList = menuList.stream().map(e -> {
            MenuCheckVo menuCheckVo = new MenuCheckVo();
            BeanUtils.copyProperties(e, menuCheckVo);
            return menuCheckVo;
        }).collect(Collectors.toList());
        MenuCheckTree menuTree = new MenuCheckTree(voList);
        return menuTree.buildTree();
    }

    @Override
    public List<SystemMenu> getAllPermissions() {
        LambdaQueryWrapper<SystemMenu> lqw = Wrappers.lambdaQuery();
        lqw.eq(SystemMenu::getIsDelte, false);
        lqw.ne(SystemMenu::getMenuType, "M");
        return dao.selectList(lqw);
    }

    /**
     * 通过用户id获取权限
     * @param userId 用户id
     * @return List
     */
    @Override
    public List<SystemMenu> findPermissionByUserId(Integer userId) {
        return dao.findPermissionByUserId(userId);
    }

    /**
     * 获取用户路由
     * @param userId 用户id
     * @return List
     */
    @Override
    public List<SystemMenu> getMenusByUserId(Integer userId) {
        return dao.getMenusByUserId(userId);
    }

    /**
     * 根据菜单id获取所有下级对象
     * @param pid 菜单id
     * @return List<SystemMenu>
     */
    private List<SystemMenu> findAllChildListByPid(Integer pid) {
        LambdaQueryWrapper<SystemMenu> lqw = Wrappers.lambdaQuery();
        lqw.eq(SystemMenu::getPid, pid);
        lqw.eq(SystemMenu::getIsDelte, false);
        return dao.selectList(lqw);
    }

    /**
     * 获取详细信息
     * @param id 菜单id
     * @return SystemMenu
     */
    private SystemMenu getInfoById(Integer id) {
        SystemMenu systemMenu = getById(id);
        if (ObjectUtil.isNull(systemMenu) || systemMenu.getIsDelte()) {
            throw new XlwebException("系统菜单不存在");
        }
        return systemMenu;
    }
}

