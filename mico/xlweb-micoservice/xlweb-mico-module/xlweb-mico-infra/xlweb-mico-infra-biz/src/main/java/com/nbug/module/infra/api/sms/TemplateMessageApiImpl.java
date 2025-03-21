package com.nbug.module.infra.api.sms;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.service.sms.TemplateMessageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class TemplateMessageApiImpl implements TemplateMessageApi {

    @Resource
    private TemplateMessageService templateMessageService;

    /**
     * 发送公众号模板消息
     * @param templateId 模板消息编号
     * @param temMap 内容Map
     * @param openId 微信用户openId
     */
    @Override
    public CommonResult<String> pushTemplateMessage(Integer templateId, HashMap<String, String> temMap, String openId) {
        templateMessageService.pushTemplateMessage(templateId, temMap, openId);
        return CommonResult.success("success");
    }

    /**
     * 发送小程序订阅消息
     * @param templateId 模板消息编号
     * @param temMap 内容Map
     * @param openId 微信用户openId
     */
    @Override
    public CommonResult<String> pushMiniTemplateMessage(Integer templateId, HashMap<String, String> temMap, String openId) {
        templateMessageService.pushMiniTemplateMessage(templateId, temMap, openId);
        return CommonResult.success("success");
    }

}
