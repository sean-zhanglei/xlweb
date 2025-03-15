package com.nbug.module.store.api.storeSeckillManger;

import com.nbug.mico.common.model.seckill.StoreSeckillManger;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.service.StoreSeckillMangerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreSeckillMangerApiImpl implements StoreSeckillMangerApi {

    @Resource
    private StoreSeckillMangerService storeSeckillMangerService;

    @Override
    public CommonResult<StoreSeckillManger> getById(Integer id) {
        return success(storeSeckillMangerService.getById(id));
    }
}
