package com.nbug.module.infra.api.sms;

import com.nbug.mico.common.model.system.SystemNotification;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.service.sms.SystemNotificationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class NotificationApiImpl implements NotificationApi {

    @Resource
    private SystemNotificationService systemNotificationService;

    /**
     * 根据标识查询信息
     * @param mark 标识
     * @return SystemNotification
     */
    @Override
    public CommonResult<SystemNotification> getByMark(String mark) {
        return CommonResult.success(systemNotificationService.getByMark(mark));
    }
}
