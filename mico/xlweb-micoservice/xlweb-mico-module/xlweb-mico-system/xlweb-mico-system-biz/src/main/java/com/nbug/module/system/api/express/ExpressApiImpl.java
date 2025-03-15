package com.nbug.module.system.api.express;

import com.nbug.mico.common.model.express.Express;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.service.ExpressService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class ExpressApiImpl implements ExpressApi {

    @Resource
    private ExpressService expressService;


    /**
     * 通过物流公司名称获取
     * @param name 物流公司名称
     */
    @Override
    public CommonResult<Express> getByName(String name) {
        return CommonResult.success(expressService.getByName(name));
    }

    /**
     * 查询快递公司
     * @param code 快递公司编号
     * @return Express
     */
    @Override
    public CommonResult<Express> getByCode(String code) {
        return CommonResult.success(expressService.getByCode(code));
    }
}
