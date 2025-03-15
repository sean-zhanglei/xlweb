package com.nbug.module.system.api.city;

import com.nbug.mico.common.model.system.SystemCity;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.service.SystemCityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class CityApiImpl implements CityApi {

    @Resource
    private SystemCityService systemCityService;


    /**
     * 根据城市名称获取城市详细数据
     * @param cityName 城市名称
     * @return 城市数据
     */
    @Override
    public CommonResult<SystemCity> getCityByCityName(String cityName) {
        return CommonResult.success(systemCityService.getCityByCityName(cityName));
    }

    /**
     * 获取城市
     * @param cityId 城市id
     * @return SystemCity
     */
    @Override
    public CommonResult<SystemCity> getCityByCityId(Integer cityId) {
        return CommonResult.success(systemCityService.getCityByCityId(cityId));
    }
}
