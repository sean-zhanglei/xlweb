package com.nbug.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.system.SystemAdmin;
import com.nbug.mico.common.model.system.SystemRole;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.SystemAdminAddRequest;
import com.nbug.mico.common.request.SystemAdminRequest;
import com.nbug.mico.common.request.SystemAdminUpdateRequest;
import com.nbug.mico.common.response.SystemAdminResponse;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.mico.common.utils.validation.ValidateFormUtil;
import com.nbug.module.system.dal.SystemAdminDao;
import com.nbug.module.system.service.SystemAdminService;
import com.nbug.module.system.service.SystemRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SystemAdminServiceImpl 接口实现

 */
@Service
public class SystemAdminServiceImpl extends ServiceImpl<SystemAdminDao, SystemAdmin> implements SystemAdminService {

    @Resource
    private SystemAdminDao dao;

    @Autowired
    private SystemRoleService systemRoleService;

    /**
     * 后台管理员列表
     * @param request 请求参数
     * @param pageParamRequest 分页参数
     * @return List
     */
    @Override
    public List<SystemAdminResponse> getList(SystemAdminRequest request, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());

        //带SystemAdminRequest类的多条件查询
        LambdaQueryWrapper<SystemAdmin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(request.getRoles())) {
            lambdaQueryWrapper.eq(SystemAdmin::getRoles, request.getRoles());
        }
        if (ObjectUtil.isNotNull(request.getStatus())) {
            lambdaQueryWrapper.eq(SystemAdmin::getStatus, request.getStatus());
        }
        if (StrUtil.isNotBlank(request.getRealName())) {
            lambdaQueryWrapper.and(i -> i.like(SystemAdmin::getRealName, request.getRealName())
                    .or().like(SystemAdmin::getAccount, request.getRealName()));
        }
        List<SystemAdmin> systemAdmins = dao.selectList(lambdaQueryWrapper);
        if (CollUtil.isEmpty(systemAdmins)) {
            return CollUtil.newArrayList();
        }
        List<SystemAdminResponse> systemAdminResponses = new ArrayList<>();
        List<SystemRole> roleList = systemRoleService.getAllList();
        for (SystemAdmin admin : systemAdmins) {
            SystemAdminResponse sar = new SystemAdminResponse();
            BeanUtils.copyProperties(admin, sar);
            sar.setLastTime(admin.getUpdateTime());
            if (StrUtil.isBlank(admin.getRoles())) continue;
            List<Integer> roleIds = XlwebUtil.stringToArrayInt(admin.getRoles());
            List<String> roleNames = new ArrayList<>();
            for (Integer roleId : roleIds) {
                List<SystemRole> hasRoles = roleList.stream().filter(e -> e.getId().equals(roleId)).collect(Collectors.toList());
                if (hasRoles.size()> 0) {
                    roleNames.add(hasRoles.stream().map(SystemRole::getRoleName).collect(Collectors.joining(",")));
                }
            }
            sar.setRoleNames(StringUtils.join(roleNames,","));
            systemAdminResponses.add(sar);
        }
        return systemAdminResponses;
    }

    /**
     * 新增管理员
     * @param systemAdminAddRequest 新增参数
     * @return Boolean
     */
    @Override
    public Boolean saveAdmin(SystemAdminAddRequest systemAdminAddRequest) {
        // 管理员名称唯一校验
        Integer result = checkAccount(systemAdminAddRequest.getAccount());
        if (result > 0) {
            throw new XlwebException("管理员已存在");
        }
        // 如果有手机号，校验手机号
        if (StrUtil.isNotBlank(systemAdminAddRequest.getPhone())) {
            ValidateFormUtil.isPhoneException(systemAdminAddRequest.getPhone());
        }

        SystemAdmin systemAdmin = new SystemAdmin();
        BeanUtils.copyProperties(systemAdminAddRequest, systemAdmin);

        String pwd = XlwebUtil.encryptPassword(systemAdmin.getPwd(), systemAdmin.getAccount());
        systemAdmin.setPwd(pwd);
        return save(systemAdmin);
    }

    /**
     * 管理员名称唯一校验
     * @param account 管理员账号
     * @return Integer
     */
    private Integer checkAccount(String account) {
        LambdaQueryWrapper<SystemAdmin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SystemAdmin::getAccount, account);
        return Math.toIntExact(dao.selectCount(lambdaQueryWrapper));

    }

    /**
     * 更新管理员
     */
    @Override
    public Boolean updateAdmin(SystemAdminUpdateRequest systemAdminRequest) {
        getDetail(systemAdminRequest.getId());
        verifyAccount(systemAdminRequest.getId(), systemAdminRequest.getAccount());
        // 如果有手机号，校验手机号
        if (StrUtil.isNotBlank(systemAdminRequest.getPhone())) {
            ValidateFormUtil.isPhoneException(systemAdminRequest.getPhone());
        }
        SystemAdmin systemAdmin = new SystemAdmin();
        BeanUtils.copyProperties(systemAdminRequest, systemAdmin);
        systemAdmin.setPwd(null);
        if (StrUtil.isNotBlank(systemAdminRequest.getPwd())) {
            String pwd = XlwebUtil.encryptPassword(systemAdminRequest.getPwd(), systemAdminRequest.getAccount());
            systemAdmin.setPwd(pwd);
        }
        return updateById(systemAdmin);
    }

    /**
     * 校验账号唯一性（管理员更新时）
     * @param id 管理员id
     * @param account 管理员账号
     */
    private void verifyAccount(Integer id, String account) {
        LambdaQueryWrapper<SystemAdmin> lqw = Wrappers.lambdaQuery();
        lqw.ne(SystemAdmin::getId, id);
        lqw.eq(SystemAdmin::getAccount, account);
        SystemAdmin systemAdmin = dao.selectOne(lqw);
        if (ObjectUtil.isNotNull(systemAdmin)) {
            throw new XlwebException("账号已存在");
        }
    }

    /**
     * 修改后台管理员状态
     * @param id 管理员id
     * @param status 状态
     * @return Boolean
     */
    @Override
    public Boolean updateStatus(Integer id, Boolean status) {
        SystemAdmin systemAdmin = getDetail(id);
        if (systemAdmin.getStatus().equals(status)) {
            return true;
        }
        systemAdmin.setStatus(status);
        return updateById(systemAdmin);
    }

    /**
     * 根据idList获取Map
     * @param adminIdList id数组
     * @return HashMap
     */
    @Override
    public HashMap<Integer, SystemAdmin> getMapInId(List<Integer> adminIdList) {
        HashMap<Integer, SystemAdmin> map = new HashMap<>();
        if (adminIdList.size() < 1) {
            return map;
        }
        LambdaQueryWrapper<SystemAdmin> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.in(SystemAdmin::getId, adminIdList);
        List<SystemAdmin> systemAdminList = dao.selectList(lambdaQueryWrapper);
        if (systemAdminList.size() < 1) {
            return map;
        }
        for (SystemAdmin systemAdmin : systemAdminList) {
            map.put(systemAdmin.getId(), systemAdmin);
        }
        return map;
    }

    /**
     * 修改后台管理员是否接收状态
     * @param id 管理员id
     * @return Boolean
     */
    @Override
    public Boolean updateIsSms(Integer id) {
        SystemAdmin systemAdmin = getDetail(id);
        if (StrUtil.isBlank(systemAdmin.getPhone())) {
            throw new XlwebException("请先为管理员添加手机号!");
        }
        systemAdmin.setIsSms(!systemAdmin.getIsSms());
        return updateById(systemAdmin);
    }

    /**
     * 获取可以接收短信的管理员
     * @return List
     */
    @Override
    public List<SystemAdmin> findIsSmsList() {
        LambdaQueryWrapper<SystemAdmin> lqw = Wrappers.lambdaQuery();
        lqw.eq(SystemAdmin::getStatus, true);
        lqw.eq(SystemAdmin::getIsDel, false);
        lqw.eq(SystemAdmin::getIsSms, true);
        List<SystemAdmin> list = dao.selectList(lqw);
        if (CollUtil.isEmpty(list)) {
            return list;
        }
        return list.stream().filter(i -> StrUtil.isNotBlank(i.getPhone())).collect(Collectors.toList());
    }

    /**
     * 管理员详情
     * @param id 管理员id
     * @return SystemAdmin
     */
    @Override
    public SystemAdmin getDetail(Integer id) {
        SystemAdmin systemAdmin = getById(id);
        if (ObjectUtil.isNull(systemAdmin) || systemAdmin.getIsDel()) {
            throw new XlwebException("管理员不存在");
        }
        return systemAdmin;
    }

    /**
     * 通过用户名获取用户
     * @param username 用户名
     * @return SystemAdmin
     */
    @Override
    public SystemAdmin selectUserByUserName(String username) {
        LambdaQueryWrapper<SystemAdmin> lqw = Wrappers.lambdaQuery();
        lqw.eq(SystemAdmin::getAccount, username);
        lqw.eq(SystemAdmin::getIsDel, false);
        return dao.selectOne(lqw);
    }

}

