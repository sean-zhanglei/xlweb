package com.nbug.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.nbug.mico.common.model.user.UserGroup;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.UserGroupRequest;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.module.user.dal.UserGroupDao;
import com.nbug.module.user.service.UserGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserGroupServiceImpl 接口实现

 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupDao, UserGroup> implements UserGroupService {

    @Resource
    private UserGroupDao dao;

    /**
    * 列表
    * @param pageParamRequest 分页类参数
    * @return List<UserGroup>
    */
    @Override
    public List<UserGroup> getList(PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        return dao.selectList(null);
    }

    /**
     * 根据id in，返回字符串拼接
     * @param groupIdValue String 分组id
     * @return List<UserTag>
     */
    @Override
    public String getGroupNameInId(String groupIdValue) {
        LambdaQueryWrapper<UserGroup> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(UserGroup::getId, XlwebUtil.stringToArray(groupIdValue)).orderByDesc(UserGroup::getId);
        List<UserGroup> userTags = dao.selectList(lambdaQueryWrapper);
        if(null == userTags){
            return "无";
        }

        return userTags.stream().map(UserGroup::getGroupName).distinct().collect(Collectors.joining(","));
    }

    /**
     * 新增用户分组
     * @param userGroupRequest 分组参数
     */
    @Override
    public Boolean create(UserGroupRequest userGroupRequest) {
        UserGroup userGroup = new UserGroup();
        BeanUtils.copyProperties(userGroupRequest, userGroup);
        return save(userGroup);
    }

    /**
     * 修改用户分组
     * @param id 分组id
     * @param userGroupRequest 修改参数
     */
    @Override
    public Boolean edit(Integer id, UserGroupRequest userGroupRequest) {
        UserGroup userGroup = new UserGroup();
        BeanUtils.copyProperties(userGroupRequest, userGroup);
        userGroup.setId(id);
        return updateById(userGroup);
    }

}

