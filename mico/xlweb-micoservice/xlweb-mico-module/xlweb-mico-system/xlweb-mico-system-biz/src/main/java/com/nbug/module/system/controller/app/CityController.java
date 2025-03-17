package com.nbug.module.system.controller.app;


import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.service.SystemCityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 城市服务

 */
@Slf4j
@RestController("CityFrontController")
@RequestMapping("system/city")
@Tag(name = "应用后台 - 城市服务")
public class CityController {

    @Autowired
    private SystemCityService systemCityService;

    /**
     * 城市服务树形结构数据
     */
    @Operation(summary = "树形结构")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<Object> register(){
        return CommonResult.success(systemCityService.getListTree());
    }
}



