package com.nbug.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.mico.common.model.system.SystemCity;
import com.nbug.mico.common.request.SystemCityRequest;
import com.nbug.mico.common.request.SystemCitySearchRequest;
import com.nbug.mico.common.vo.SystemCityTreeVo;

import java.util.List;

/**
 * SystemCityService 接口

 */
public interface SystemCityService extends IService<SystemCity> {

    /**
     * 分页城市列表
     * @param request 搜索条件
     */
    Object getList(SystemCitySearchRequest request);

    /**
     * 修改状态
     * @param id 城市id
     * @param status 状态
     */
    Boolean updateStatus(Integer id, Boolean status);

    /**
     * 修改城市
     * @param id 城市id
     * @param request 修改参数
     */
    Boolean update(Integer id, SystemCityRequest request);

    /**
     * 获取城市树
     */
    List<SystemCityTreeVo> getListTree();

    /**
     * 获取所有城市cityId
     * @return List<Integer>
     */
    List<Integer> getCityIdList();

    /**
     * 获取城市
     * @param cityId 城市id
     * @return SystemCity
     */
    SystemCity getCityByCityId(Integer cityId);

    /**
     * 根据城市名称获取城市详细数据
     * @param cityName 城市名称
     * @return 城市数据
     */
    SystemCity getCityByCityName(String cityName);
}
