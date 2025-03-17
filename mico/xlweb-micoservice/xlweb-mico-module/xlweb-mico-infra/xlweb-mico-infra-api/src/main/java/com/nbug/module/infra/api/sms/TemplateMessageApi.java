package com.nbug.module.infra.api.sms;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 模板消息")
public interface TemplateMessageApi {

    String PREFIX = ApiConstants.PREFIX + "/templateMessage";

    @PostMapping(PREFIX + "/pushTemplateMessage")
    @Operation(summary = "发送公众号模板消息")
    @Parameters({
            @Parameter(name = "templateId", description = "模板id", required = true),
            @Parameter(name = "temMap", description = "模板内容", required = true),
            @Parameter(name = "openId", description = "用户openId", required = true)
    })
    public CommonResult<String> pushTemplateMessage(@RequestParam Integer templateId,
                                                    @RequestParam HashMap<String, String> temMap,
                                                    @RequestParam String openId);

    @PostMapping(PREFIX + "/pushMiniTemplateMessage")
    @Operation(summary = "发送小程序模板消息")
    @Parameters({
            @Parameter(name = "templateId", description = "模板id", required = true),
            @Parameter(name = "temMap", description = "模板内容", required = true),
            @Parameter(name = "openId", description = "用户openId", required = true)
    })
    public CommonResult<String> pushMiniTemplateMessage(@RequestParam Integer templateId,
                                                        @RequestParam HashMap<String, String> temMap,
                                                        @RequestParam String openId);

}
