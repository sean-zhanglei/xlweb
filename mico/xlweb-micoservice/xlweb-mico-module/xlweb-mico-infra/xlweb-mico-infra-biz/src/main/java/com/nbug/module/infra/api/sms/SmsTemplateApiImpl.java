package com.nbug.module.infra.api.sms;

import com.nbug.mico.common.model.sms.SmsTemplate;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.service.sms.SmsTemplateService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class SmsTemplateApiImpl implements SmsTemplateApi {

    @Resource
    private SmsTemplateService smsTemplateService;

    /**
     * 获取模版详情
     * @param id 模板id
     * @return SmsTemplate
     */
    @Override
    public CommonResult<SmsTemplate> getDetail(Integer id) {
        return CommonResult.success(smsTemplateService.getDetail(id));
    }

}
