package com.nbug.module.infra.api.yly;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.service.Yly.YlyPrintService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class YlyApiImpl implements  YlyApiApi{

    @Resource
    private YlyPrintService ylyPrintService;

    /**
     * 易联云打印商品信息
     * @param ordId 订单id
     * @param isAuto 是否自动打印
     */
    @Override
    public CommonResult<String> YlyPrint(String ordId, boolean isAuto) {
        ylyPrintService.YlyPrint(ordId, isAuto);
        return CommonResult.success("success");
    }

}
