package com.nbug.module.system.api.systemMenu;

import com.nbug.mico.common.model.system.SystemMenu;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.service.SystemMenuService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class SystemMenuApiImpl implements SystemMenuApi {

    @Resource
    private SystemMenuService systemMenuService;


    /**
     * 获取所有权限
     * @return List
     */
    @Override
    public CommonResult<List<SystemMenu>> getAllPermissions() {
        return CommonResult.success(systemMenuService.getAllPermissions());
    }

    /**
     * 通过用户id获取权限
     */
    @Override
    public CommonResult<List<SystemMenu>> findPermissionByUserId(Integer userId) {
        return CommonResult.success(systemMenuService.findPermissionByUserId(userId));
    }
}
