package com.nbug.module.system.api.logistic;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.vo.LogisticsResultVo;
import com.nbug.module.system.service.LogisticService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class LogisticApiImpl implements LogisticApi {

    @Resource
    private LogisticService logisticService;

    @Override
    public CommonResult<LogisticsResultVo> info(String expressNo, String type, String com, String phone) {
        return CommonResult.success(logisticService.info(expressNo, type, com, phone));
    }
}
