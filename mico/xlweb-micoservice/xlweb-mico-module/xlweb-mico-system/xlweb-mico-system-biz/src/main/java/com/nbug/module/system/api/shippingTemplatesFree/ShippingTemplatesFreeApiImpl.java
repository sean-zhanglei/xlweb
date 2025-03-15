package com.nbug.module.system.api.shippingTemplatesFree;

import com.nbug.mico.common.model.express.ShippingTemplatesFree;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.service.ShippingTemplatesFreeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class ShippingTemplatesFreeApiImpl implements ShippingTemplatesFreeApi {

    @Resource
    private ShippingTemplatesFreeService shippingTemplatesFreeService;


    /**
     * 根据模板编号、城市ID查询
     * @param tempId 模板编号
     * @param cityId 城市ID
     * @return 运费模板
     */
    @Override
    public CommonResult<ShippingTemplatesFree> getByTempIdAndCityId(Integer tempId, Integer cityId) {
        return CommonResult.success(shippingTemplatesFreeService.getByTempIdAndCityId(tempId, cityId));
    }
}
