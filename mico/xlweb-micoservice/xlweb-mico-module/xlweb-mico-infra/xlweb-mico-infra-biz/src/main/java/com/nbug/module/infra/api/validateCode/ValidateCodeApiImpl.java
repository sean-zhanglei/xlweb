package com.nbug.module.infra.api.validateCode;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.service.validateCode.ValidateCodeService;
import com.nbug.mico.common.vo.ValidateCode;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class ValidateCodeApiImpl implements ValidateCodeApi {

    @Resource
    private ValidateCodeService validateCodeService;

    /**
     * 验证验证码
     */
    @Override
    public CommonResult<Boolean> check(String key, String code) {
        return CommonResult.success(validateCodeService.check(key, code));
    }

    /**
     * 获取图片验证码
     * @return CommonResult
     */
    @Override
    public CommonResult<ValidateCode> get() {
        return CommonResult.success(validateCodeService.get());
    }
}
