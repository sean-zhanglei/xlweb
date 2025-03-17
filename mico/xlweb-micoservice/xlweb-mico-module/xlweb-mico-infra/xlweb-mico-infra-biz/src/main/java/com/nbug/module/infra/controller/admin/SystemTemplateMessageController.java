package com.nbug.module.infra.controller.admin;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.service.sms.TemplateMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;


/**
 * 微信模板 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("infra/wechat/template")
@Tag(name = "管理后台 - 微信 -- 消息模版") //配合swagger使用
@Validated
public class SystemTemplateMessageController {

    @Autowired
    private TemplateMessageService templateMessageService;

    /**
     * 公众号模板消息同步
     */
    @PreAuthorize("hasAuthority('admin:wechat:whcbqhn:sync')")
    @Operation(summary = "公众号模板消息同步")
    @RequestMapping(value = "/whcbqhn/sync", method = RequestMethod.POST)
    public CommonResult<String> whcbqhnSync() {
        if (templateMessageService.whcbqhnSync()) {
            return CommonResult.success("公众号模板消息同步成功");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR,"公众号模板消息同步失败");
    }

    /**
     * 小程序订阅消息同步
     */
    @PreAuthorize("hasAuthority('admin:wechat:routine:sync')")
    @Operation(summary = "小程序订阅消息同步")
    @RequestMapping(value = "/routine/sync", method = RequestMethod.POST)
    public CommonResult<String> routineSync() {
        if (templateMessageService.routineSync()) {
            return CommonResult.success("小程序订阅消息同步成功");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR,"小程序订阅消息同步失败");
    }
}



