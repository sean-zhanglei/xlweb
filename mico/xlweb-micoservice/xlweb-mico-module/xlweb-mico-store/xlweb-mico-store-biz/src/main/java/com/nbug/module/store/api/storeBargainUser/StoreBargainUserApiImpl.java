package com.nbug.module.store.api.storeBargainUser;

import com.nbug.mico.common.model.bargain.StoreBargainUser;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.service.StoreBargainUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreBargainUserApiImpl implements StoreBargainUserApi {

    @Resource
    private StoreBargainUserService storeBargainUserService;

    @Override
    public CommonResult<StoreBargainUser> getById(Integer id) {
        return success(storeBargainUserService.getById(id));
    }

}
