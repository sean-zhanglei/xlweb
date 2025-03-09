package com.nbug.module.system.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.common.model.system.SystemFormTemp;
import com.nbug.common.request.PageParamRequest;
import com.nbug.common.request.SystemFormCheckRequest;
import com.nbug.common.request.SystemFormTempRequest;
import com.nbug.common.request.SystemFormTempSearchRequest;

import java.util.List;

/**
 * SystemFormTempService 接口

 */
public interface SystemFormTempService extends IService<SystemFormTemp> {

    /**
     * 列表
     * @param request 请求参数
     * @param pageParamRequest 分页类参数
     * @return List<SystemFormTemp>
     */
    List<SystemFormTemp> getList(SystemFormTempSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 验证item规则
     * @param systemFormCheckRequest SystemFormCheckRequest 表单数据提交
     */
    void checkForm(SystemFormCheckRequest systemFormCheckRequest);

    /**
     * 新增表单模板
     * @param systemFormTempRequest 新增参数
     */
    Boolean add(SystemFormTempRequest systemFormTempRequest);

    /**
     * 修改表单模板
     * @param id integer id
     * @param systemFormTempRequest 修改参数
     */
    Boolean edit(Integer id, SystemFormTempRequest systemFormTempRequest);
}
