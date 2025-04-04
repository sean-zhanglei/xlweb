package com.nbug.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.mico.common.model.system.SystemGroup;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.SystemGroupRequest;
import com.nbug.mico.common.request.SystemGroupSearchRequest;

import java.util.List;

/**
 * SystemGroupService 接口

 */
public interface SystemGroupService extends IService<SystemGroup> {

    /**
     * 分页显示组合数据表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    List<SystemGroup> getList(SystemGroupSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 新增组合数据
     * @param systemGroupRequest 新增参数
     */
    Boolean add(SystemGroupRequest systemGroupRequest);

    /**
     * 删除组合数据表
     * @param id Integer
     */
    Boolean delete(Integer id);

    /**
     * 修改组合数据表
     * @param id integer id
     * @param systemGroupRequest 修改参数
     */
    Boolean edit(Integer id, SystemGroupRequest systemGroupRequest);
}
