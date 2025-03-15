package com.nbug.module.infra.controller.admin;

import com.nbug.mico.common.model.system.SystemNotification;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.NotificationInfoRequest;
import com.nbug.mico.common.request.NotificationSearchRequest;
import com.nbug.mico.common.request.NotificationUpdateRequest;
import com.nbug.mico.common.response.NotificationInfoResponse;
import com.nbug.module.infra.service.sms.SystemNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;

/**
 * 通知设置 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/infra/system/notification")
@Tag(name = "通知设置-前端控制器") //配合swagger使用
public class SystemNotificationController {

    @Autowired
    private SystemNotificationService systemNotificationService;

    /**
     * 系统通知列表
     * @param request ExpressSearchRequest 搜索条件
     */
    @PreAuthorize("hasAuthority('admin:system:notification:list')")
    @Operation(summary = "系统通知列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<List<SystemNotification>> getList(@Validated NotificationSearchRequest request) {
        return CommonResult.success(systemNotificationService.getList(request));
    }

    /**
     * 公众号模板开关
     */
    @PreAuthorize("hasAuthority('admin:system:notification:wechat:switch')")
    @Operation(summary = "公众号模板开关")
    @RequestMapping(value = "/wechat/switch/{id}", method = RequestMethod.POST)
    public CommonResult<Object> wechatSwitch(@PathVariable Integer id) {
        if (systemNotificationService.wechatSwitch(id)) {
            return CommonResult.success("更改成功");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR, "更改失败");
    }

    /**
     * 小程序订阅模板开关
     */
    @PreAuthorize("hasAuthority('admin:system:notification:routine:switch')")
    @Operation(summary = "小程序订阅模板开关")
    @RequestMapping(value = "/routine/switch/{id}", method = RequestMethod.POST)
    public CommonResult<Object> routineSwitch(@PathVariable Integer id) {
        if (systemNotificationService.routineSwitch(id)) {
            return CommonResult.success("更改成功");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR, "更改失败");
    }

    /**
     * 发送短信开关
     */
    @PreAuthorize("hasAuthority('admin:system:notification:sms:switch')")
    @Operation(summary = "发送短信开关")
    @RequestMapping(value = "/sms/switch/{id}", method = RequestMethod.POST)
    public CommonResult<Object> smsSwitch(@PathVariable Integer id) {
        if (systemNotificationService.smsSwitch(id)) {
            return CommonResult.success("更改成功");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR, "更改失败");
    }

    /**
     * 通知详情
     */
    @PreAuthorize("hasAuthority('admin:system:notification:detail')")
    @Operation(summary = "通知详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public CommonResult<NotificationInfoResponse> info(@Validated NotificationInfoRequest request) {
        return CommonResult.success(systemNotificationService.getDetail(request));
    }

    /**
     * 修改通知
     */
    @PreAuthorize("hasAuthority('admin:system:notification:update')")
    @Operation(summary = "修改通知")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@Validated @RequestBody NotificationUpdateRequest request) {
        if (systemNotificationService.modify(request)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }
}



