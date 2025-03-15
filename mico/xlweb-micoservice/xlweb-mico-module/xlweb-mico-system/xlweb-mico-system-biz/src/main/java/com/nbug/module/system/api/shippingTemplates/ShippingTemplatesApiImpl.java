package com.nbug.module.system.api.shippingTemplates;

import com.nbug.mico.common.model.express.ShippingTemplates;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.service.ShippingTemplatesService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class ShippingTemplatesApiImpl implements ShippingTemplatesApi {

    @Resource
    private ShippingTemplatesService shippingTemplatesService;

    @Override
    public CommonResult<ShippingTemplates> getById(Integer id) {
        return CommonResult.success(shippingTemplatesService.getById(id));
    }
}
