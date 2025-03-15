package com.nbug.module.system.api.admin;

import com.nbug.mico.common.model.system.SystemAdmin;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.service.SystemAdminService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class AdminApiImpl implements AdminApi {

    @Resource
    private SystemAdminService systemAdminService;


    /**
     * 根据idList获取Map
     * @param adminIdList id数组
     * @return HashMap
     */
    @Override
    public CommonResult<HashMap<Integer, SystemAdmin>> getMapInId(List<Integer> adminIdList) {
        return CommonResult.success(systemAdminService.getMapInId(adminIdList));
    }

    /**
     * 获取可以接收短信的管理员
     */
    @Override
    public CommonResult<List<SystemAdmin>> findIsSmsList() {
        return CommonResult.success(systemAdminService.findIsSmsList());
    }
}
