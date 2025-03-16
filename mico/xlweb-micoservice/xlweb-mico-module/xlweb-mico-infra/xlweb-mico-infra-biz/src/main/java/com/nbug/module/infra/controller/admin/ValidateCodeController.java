package com.nbug.module.infra.controller.admin;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.vo.ValidateCode;
import com.nbug.module.infra.service.validateCode.ValidateCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 验证码服务

 */
@Slf4j
@RestController
@RequestMapping("api/admin/validate/code")
@Tag(name = "验证码服务")
public class ValidateCodeController {

    @Autowired
    private ValidateCodeService validateCodeService;

    /**
     * 获取图片验证码
     * @return CommonResult
     */
//    @PreAuthorize("hasAuthority('admin:validate:code:get')")
    @Operation(summary="获取验证码")
    @GetMapping(value = "/get")
    public CommonResult<ValidateCode> get() {
        ValidateCode validateCode = validateCodeService.get();
        return CommonResult.success(validateCode);
    }
}



