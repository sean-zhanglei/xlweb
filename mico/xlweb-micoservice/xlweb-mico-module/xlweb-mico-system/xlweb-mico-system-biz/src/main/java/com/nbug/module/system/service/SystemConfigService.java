package com.nbug.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.mico.common.model.system.SystemConfig;
import com.nbug.mico.common.request.SystemConfigAdminRequest;
import com.nbug.mico.common.request.SystemFormCheckRequest;
import com.nbug.mico.common.vo.ExpressSheetVo;

import java.util.HashMap;
import java.util.List;

/**
 * SystemConfigService 接口

 */
public interface SystemConfigService extends IService<SystemConfig> {

    /**
     * 根据menu name 获取 value
     * @param key menu name
     * @return String
     */
    String getValueByKey(String key);

    /**
     * 同时获取多个配置
     * @param keys 多个配置key
     * @return 查询到的多个结果
     */
    List<String> getValuesByKes(List<String> keys);

    /**
     * 保存或更新配置数据
     * @param name 菜单名称
     * @param value 菜单值
     * @return Boolean
     */
    Boolean updateOrSaveValueByName(String name, String value);

    /**
     * 根据 name 获取 value 找不到抛异常
     * @param key menu name
     * @return String
     */
    String getValueByKeyException(String key);

    /**
     * 整体保存表单数据
     * @param systemFormCheckRequest SystemFormCheckRequest 数据保存
     * @return Boolean
     */
    Boolean saveForm(SystemFormCheckRequest systemFormCheckRequest);

    /**
     * 根据formId查询数据
     * @param formId Integer id
     * @return HashMap<String, String>
     */
    HashMap<String, String> info(Integer formId);

    /**
     * 根据name查询数据
     * @param name name
     * @return Boolean
     */
    Boolean checkName(String name);

    /**
     * 根据key获取配置
     * @param key key
     * @return List
     */
    List<SystemConfig> getListByKey(String key);

    /**
     * 获取面单默认配置信息
     * @return ExpressSheetVo
     */
    ExpressSheetVo getDeliveryInfo();

    /**
     * 更新配置信息
     * @param requestList 请求数组
     * @return Boolean
     */
    Boolean updateByList(List<SystemConfigAdminRequest> requestList);

    /**
     * 获取颜色配置
     * @return SystemConfig
     */
    SystemConfig getColorConfig();
}
