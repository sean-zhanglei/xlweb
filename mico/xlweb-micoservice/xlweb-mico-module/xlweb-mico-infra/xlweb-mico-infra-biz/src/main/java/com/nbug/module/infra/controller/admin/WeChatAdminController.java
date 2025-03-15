package com.nbug.module.infra.controller.admin;


import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.response.WeChatJsSdkConfigResponse;
import com.nbug.module.infra.service.wechat.WechatNewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信 -- 开放平台 admin

 */
@Slf4j
@RestController("WeChatAdminController")
@RequestMapping("api/admin/infra/wechat")
@Tag(name = "微信 -- 开放平台 admin")
public class WeChatAdminController {

    @Autowired
    private WechatNewService wechatNewService;

    /**
     * 获取微信公众号js配置
     */
    @PreAuthorize("hasAuthority('admin:wechat:config')")
    @Operation(summary = "获取微信公众号js配置")
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    @Parameter(name = "url", description = "页面地址url")
    public CommonResult<WeChatJsSdkConfigResponse> configJs(@RequestParam(value = "url") String url) {
        return CommonResult.success(wechatNewService.getJsSdkConfig(url));
    }
}
