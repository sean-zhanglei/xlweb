package com.nbug.module.system.api.systemUserLevel;

import com.nbug.mico.common.model.system.SystemUserLevel;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.service.SystemUserLevelService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class SystemUserLevelApiImpl implements SystemUserLevelApi {

    @Resource
    private SystemUserLevelService systemUserLevelService;


    @Override
    public CommonResult<SystemUserLevel> getByLevelId(Integer levelId) {
        return CommonResult.success(systemUserLevelService.getByLevelId(levelId));
    }

    /**
     * 获取可用等级列表
     * @return List
     */
    @Override
    public CommonResult<List<SystemUserLevel>> getUsableList() {
        return CommonResult.success(systemUserLevelService.getUsableList());
    }

    /**
     * 获取系统等级列表（移动端）
     */
    @Override
    public CommonResult<List<SystemUserLevel>> getH5LevelList() {
        return CommonResult.success(systemUserLevelService.getH5LevelList());
    }
}
