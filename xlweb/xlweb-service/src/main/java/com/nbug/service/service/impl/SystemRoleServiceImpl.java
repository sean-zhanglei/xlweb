package com.nbug.service.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbug.common.constants.Constants;
import com.nbug.common.exception.XlwebException;
import com.nbug.common.model.system.SystemAdmin;
import com.nbug.common.model.system.SystemMenu;
import com.nbug.common.model.system.SystemRole;
import com.nbug.common.model.system.SystemRoleMenu;
import com.nbug.common.request.PageParamRequest;
import com.nbug.common.request.SystemRoleRequest;
import com.nbug.common.request.SystemRoleSearchRequest;
import com.nbug.common.response.RoleInfoResponse;
import com.nbug.common.utils.XlwebUtil;
import com.nbug.common.utils.SecurityUtil;
import com.nbug.common.vo.CategoryTreeVo;
import com.nbug.common.vo.LoginUserVo;
import com.nbug.common.vo.MenuCheckTree;
import com.nbug.common.vo.MenuCheckVo;
import com.github.pagehelper.PageHelper;
import com.nbug.service.dao.SystemRoleDao;
import com.nbug.service.service.CategoryService;
import com.nbug.service.service.SystemMenuService;
import com.nbug.service.service.SystemRoleMenuService;
import com.nbug.service.service.SystemRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SystemRoleServiceImpl 接口实现

 */
@Service
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleDao, SystemRole> implements SystemRoleService {

    @Resource
    private SystemRoleDao dao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private SystemRoleMenuService systemRoleMenuService;

    @Autowired
    private SystemMenuService systemMenuService;

    /**
     * 获取所有角色
     * @return List
     */
    @Override
    public List<SystemRole> getAllList() {
        LambdaQueryWrapper<SystemRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SystemRole::getStatus, true);
        lambdaQueryWrapper.orderByAsc(SystemRole::getId);
        return dao.selectList(lambdaQueryWrapper);
    }

    /**
    * 列表
    * @param request 请求参数
    * @param pageParamRequest 分页类参数
    * @return List<SystemRole>
    */
    @Override
    public List<SystemRole> getList(SystemRoleSearchRequest request, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<SystemRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(SystemRole::getId, SystemRole::getRoleName, SystemRole::getStatus,
                SystemRole::getCreateTime, SystemRole::getUpdateTime);
        if (ObjectUtil.isNotNull(request.getStatus())) {
            lambdaQueryWrapper.eq(SystemRole::getStatus, request.getStatus());
        }
        if (ObjectUtil.isNotNull(request.getRoleName())) {
            lambdaQueryWrapper.like(SystemRole::getRoleName, request.getRoleName());
        }
        lambdaQueryWrapper.orderByAsc(SystemRole::getId);
        return dao.selectList(lambdaQueryWrapper);
    }

    /**
     * 根据id集合获取对应权限列表
     * @param ids id集合
     * @return 对应的权限列表
     */
    @Override
    public List<SystemRole> getListInIds(List<Integer> ids) {
        return dao.selectBatchIds(ids);
    }

    /**
     * 带结构的无线级分类
     */
    @Override
    public List<CategoryTreeVo> menu() {
        List<Integer> categoryIdList = getRoleListInRoleId();
        System.out.println("权限列表:categoryIdList:"+ JSON.toJSONString(categoryIdList));
        return categoryService.getListTree(Constants.CATEGORY_TYPE_MENU, 1, categoryIdList);
    }

    /**
     * 修改身份状态
     */
    @Override
    public Boolean updateStatus(Integer id, Boolean status) {
        SystemRole role = getById(id);
        if (ObjectUtil.isNull(role)) {
            throw new XlwebException("身份不存在");
        }
        if (role.getStatus().equals(status)) {
            return true;
        }
        role.setStatus(status);
        return updateById(role);
    }

    /**
     * 添加身份
     * @param systemRoleRequest 身份参数
     * @return Boolean
     */
    @Override
    public Boolean add(SystemRoleRequest systemRoleRequest) {
        if (existName(systemRoleRequest.getRoleName(), null)) {
            throw new XlwebException("角色名称重复");
        }
        List<Integer> ruleList = Stream.of(systemRoleRequest.getRules().split(",")).map(Integer::valueOf).distinct().collect(Collectors.toList());
        SystemRole systemRole = new SystemRole();
        BeanUtils.copyProperties(systemRoleRequest, systemRole);
        systemRole.setId(null);
        systemRole.setRules("");

        return transactionTemplate.execute(e -> {
            boolean save = save(systemRole);
            if (!save) {
                return Boolean.FALSE;
            }
            List<SystemRoleMenu> roleMenuList = ruleList.stream().map(rule -> {
                SystemRoleMenu roleMenu = new SystemRoleMenu();
                roleMenu.setRid(systemRole.getId());
                roleMenu.setMenuId(rule);
                return roleMenu;
            }).collect(Collectors.toList());
            systemRoleMenuService.saveBatch(roleMenuList, 100);
            return Boolean.TRUE;
        });
    }

    /**
     * 判断角色名称是否存在
     * @param roleName 角色名称
     * @param id 角色id
     * @return Boolean
     */
    private Boolean existName(String roleName, Integer id) {
        LambdaQueryWrapper<SystemRole> lqw = Wrappers.lambdaQuery();
        lqw.eq(SystemRole::getRoleName, roleName);
        if (ObjectUtil.isNotNull(id)) {
            lqw.ne(SystemRole::getId, id);
        }
        lqw.last(" limit 1");
        Integer count = dao.selectCount(lqw);
        return count > 0;
    }

    /**
     * 修改身份管理表
     * @param systemRoleRequest 修改参数
     */
    @Override
    public Boolean edit(SystemRoleRequest systemRoleRequest) {
        SystemRole role = getById(systemRoleRequest.getId());
        if (ObjectUtil.isNull(role)) {
            throw new XlwebException("角色不存在");
        }
        if (!role.getRoleName().equals(systemRoleRequest.getRoleName())) {
            if (existName(systemRoleRequest.getRoleName(), systemRoleRequest.getId())) {
                throw new XlwebException("角色名称重复");
            }
        }
        SystemRole systemRole = new SystemRole();
        BeanUtils.copyProperties(systemRoleRequest, systemRole);
        systemRole.setRules("");
        List<Integer> ruleList = Stream.of(systemRoleRequest.getRules().split(",")).map(Integer::valueOf).distinct().collect(Collectors.toList());
        List<SystemRoleMenu> roleMenuList = ruleList.stream().map(rule -> {
            SystemRoleMenu roleMenu = new SystemRoleMenu();
            roleMenu.setRid(systemRole.getId());
            roleMenu.setMenuId(rule);
            return roleMenu;
        }).collect(Collectors.toList());
        return transactionTemplate.execute(e -> {
            updateById(systemRole);
            systemRoleMenuService.deleteByRid(systemRole.getId());
            systemRoleMenuService.saveBatch(roleMenuList, 100);
            return Boolean.TRUE;
        });
    }

    /**
     * 删除角色
     * @param id 角色id
     * @return Boolean
     */
    @Override
    public Boolean delete(Integer id) {
        SystemRole systemRole = getById(id);
        if (ObjectUtil.isNull(systemRole)) {
            throw new XlwebException("角色已删除");
        }
        return transactionTemplate.execute(e -> {
            dao.deleteById(id);
            systemRoleMenuService.deleteByRid(id);
            return Boolean.TRUE;
        });
    }

    /**
     * 获取角色详情
     * @param id 角色id
     * @return RoleInfoResponse
     */
    @Override
    public RoleInfoResponse getInfo(Integer id) {
        SystemRole systemRole = getById(id);
        if (ObjectUtil.isNull(systemRole)) {
            throw new XlwebException("角色不存在");
        }
        // 查询角色对应的菜单(权限)
        List<SystemMenu> menuList = systemMenuService.getCacheList();
        List<Integer> menuIdList = systemRoleMenuService.getMenuListByRid(id);

        List<MenuCheckVo> menuCheckVoList = menuList.stream().map(menu -> {
            MenuCheckVo menuCheckVo = new MenuCheckVo();
            BeanUtils.copyProperties(menu, menuCheckVo);
            if (menuIdList.contains(menu.getId())) {
                menuCheckVo.setChecked(true);
            } else {
                menuCheckVo.setChecked(false);
            }
            return menuCheckVo;
        }).collect(Collectors.toList());

        RoleInfoResponse response = new RoleInfoResponse();
        BeanUtils.copyProperties(systemRole, response);
        response.setMenuList(new MenuCheckTree(menuCheckVoList).buildTree());
        return response;
    }

    private List<Integer> getRoleListInRoleId(){
        //获取当前用户的所有权限
//        SystemAdmin systemAdmin = systemAdminService.getInfo();
        LoginUserVo loginUserVo = SecurityUtil.getLoginUserVo();
        SystemAdmin systemAdmin = loginUserVo.getUser();
        if (null == systemAdmin || StringUtils.isBlank(systemAdmin.getRoles())){
            throw new XlwebException("没有权限访问！");
        }

        //获取用户权限组
        List<SystemRole> systemRoleList = getVoListInId(systemAdmin.getRoles());
        if (systemRoleList.size() < 1){
            throw new XlwebException("没有权限访问！");
        }

        //获取用户权限规则
        List<Integer> categoryIdList = new ArrayList<>();
        for (SystemRole systemRole : systemRoleList) {
            if (StringUtils.isBlank(systemRole.getRules())){
                continue;
            }

            categoryIdList.addAll(XlwebUtil.stringToArray(systemRole.getRules()));
        }

        return categoryIdList;
    }

    private List<SystemRole> getVoListInId(String roles) {
        LambdaQueryWrapper<SystemRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SystemRole::getId, XlwebUtil.stringToArray(roles));
        return dao.selectList(lambdaQueryWrapper);
    }

}

